package com.planradar.weatherapptask.di.modules

import com.example.halanchallenge.di.qualifiers.NetworkClient
import com.google.gson.GsonBuilder
import com.planradar.weatherapptask.data.remote.api.CityApi
import com.planradar.weatherapptask.util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @NetworkClient
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpBuilder = OkHttpClient().newBuilder()
            .readTimeout(1, TimeUnit.MINUTES)
            .connectTimeout(30, TimeUnit.SECONDS)
            .pingInterval(30, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .retryOnConnectionFailure(true)
            .addNetworkInterceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder()
                request
                    .addHeader("Content-Type", "application/json")
                    .method(original.method, original.body)
                chain.proceed(request.build())

            }

        return okHttpBuilder.build()
    }


    @Provides
    fun provideCityApi(@NetworkClient okHttpClient: OkHttpClient): CityApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .client(okHttpClient)
            .build()
            .create(CityApi::class.java)

    }


}