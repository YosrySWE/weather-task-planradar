package com.planradar.weatherapptask.presentation.platform

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.planradar.weatherapptask.R
import com.planradar.weatherapptask.util.extensions.connectivityManager
import com.planradar.weatherapptask.util.extensions.withColor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.reflect.ParameterizedType

//typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    private var _binding: VB? = null
    val binding: VB get() = _binding!!

    abstract val viewModel: ViewModel

    fun showSnack(text: String){
        Snackbar.make(
            requireActivity().findViewById(android.R.id.content),
            text,
            Snackbar.LENGTH_SHORT
        )
            .withColor(
                requireContext().resources.getColor(
                    R.color.strong_blue,
                    resources.newTheme()
                )
            )
            .setTextColor(
                requireContext().resources.getColor(
                    R.color.white,
                    resources.newTheme()
                )
            )
            .show()
    }
    private val networkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .build()

    @Suppress("UNCHECKED_CAST")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val type = javaClass.genericSuperclass
        val clazz = (type as ParameterizedType).actualTypeArguments[0] as Class<*>
        val method = clazz.getMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        )
        _binding = method.invoke(null, layoutInflater, container, false) as VB?
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireContext().connectivityManager.requestNetwork(networkRequest, networkCallback)
        render()
    }


    open var isOnline: Boolean = false
    abstract fun render()


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    abstract fun online()

    abstract fun offline()

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        // network is available for use
        @RequiresApi(Build.VERSION_CODES.M)
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            lifecycleScope.launch(Dispatchers.Main) {
                online()
            }
        }

        // lost network connection
        @RequiresApi(Build.VERSION_CODES.M)
        override fun onLost(network: Network) {
            super.onLost(network)
            lifecycleScope.launch(Dispatchers.Main) {
                offline()
            }
        }
    }
}

