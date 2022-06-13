package com.planradar.weatherapptask.presentation.features.weather_history

import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.planradar.weatherapptask.R
import com.planradar.weatherapptask.databinding.FragmentWeatherHistoryBinding
import com.planradar.weatherapptask.domain.model.Weather
import com.planradar.weatherapptask.presentation.features.details.DetailsIntent
import com.planradar.weatherapptask.presentation.features.details.DetailsViewState
import com.planradar.weatherapptask.presentation.features.weather_history.adapter.HistoryAdapter
import com.planradar.weatherapptask.presentation.platform.BaseFragment
import com.planradar.weatherapptask.util.extensions.*
import com.planradar.weatherapptask.util.formatFetchDate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WeatherHistoryFragment : BaseFragment<FragmentWeatherHistoryBinding>(),
    HistoryAdapter.ItemClickListener {
    override val viewModel: WeatherHistoryViewModel by viewModels()
    val args: WeatherHistoryFragmentArgs by navArgs()
    lateinit var adapter: HistoryAdapter
    override fun render() {
        binding.header.backBtn.show()
        binding.header.tvTitle.text = "${args.cityName} historical"
        binding.header.backBtn.setOnClickListener { view ->
            findNavController().navigateUp()
            view.disableTemp()
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                viewModel.state.collect {
                    when(it){
                        is WeatherHistoryViewState.Idle ->{
                            viewModel.intentChannel.trySend(WeatherHistoryIntent.Init(args.cityId))
                        }
                        is WeatherHistoryViewState.Empty ->{
                            if(adapter.currentList.isEmpty()){
                                empty()
                            }
                        }
                        is WeatherHistoryViewState.IsLoading ->{
                            loading(it.isLoading)
                        }
                        is WeatherHistoryViewState.Success ->{
                            loading(false)
                            adapter = HistoryAdapter(this@WeatherHistoryFragment)
                            binding.listHistory.show()
                            binding.emptyView.secret()
                            binding.listHistory.layoutManager = LinearLayoutManager(requireContext())
                            binding.listHistory.adapter = adapter
                            adapter.submitList(it.list)

                        }
                        is WeatherHistoryViewState.Error ->{
                            loading(false)
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        }

                    }
                }
            }
            repeatOnLifecycle(Lifecycle.State.DESTROYED){
                viewModel.clear()
            }
        }
    }

    private fun loading(flag: Boolean){
        if (flag) {
            binding.progress.visibility = View.VISIBLE
        } else {
            binding.progress.visibility = View.GONE
        }
    }


    override fun online() {
    }

    override fun offline() {
    }

    fun empty(){
        //Weather History of $cityName doesn't exist.
        binding.emptyView.show()
        binding.listHistory.secret()
        binding.progress.secret()
    }

    override fun onItemClick(item: Weather) {

        val dest = WeatherHistoryFragmentDirections.actionFragmentWeatherHistoryToFragmentDetails(null, item)
        findNavController().safeNavigate(dest)
    }
}