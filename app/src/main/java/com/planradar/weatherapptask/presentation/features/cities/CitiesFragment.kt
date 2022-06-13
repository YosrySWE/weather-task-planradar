package com.planradar.weatherapptask.presentation.features.cities

import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.planradar.weatherapptask.R
import com.planradar.weatherapptask.data.remote.dto.CityResponse
import com.planradar.weatherapptask.databinding.FragmentCitiesBinding
import com.planradar.weatherapptask.domain.model.City
import com.planradar.weatherapptask.presentation.features.cities.adapter.CitiesAdapter
import com.planradar.weatherapptask.presentation.features.search.SearchDialog
import com.planradar.weatherapptask.presentation.platform.BaseFragment
import com.planradar.weatherapptask.util.extensions.disableTemp
import com.planradar.weatherapptask.util.extensions.safeNavigate
import com.planradar.weatherapptask.util.extensions.secret
import com.planradar.weatherapptask.util.extensions.withColor
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CitiesFragment : BaseFragment<FragmentCitiesBinding>(), CitiesAdapter.ItemClickListener,
    SearchDialog.Callback {
    override val viewModel: CitiesViewModel by viewModels()
    lateinit var adapter: CitiesAdapter

    override var isOnline: Boolean = true
    override fun render() {
        binding.header.backBtn.secret()
        binding.header.tvTitle.text = getString(R.string.title_cities_fragment)
        binding.btnAddCity.setOnClickListener {
            if (!isOnline) {
                showSnack("You are offline")
                return@setOnClickListener
            }
            val blankFragment = SearchDialog.newInstance(this)
            blankFragment.show(childFragmentManager, blankFragment.tag)
            it.disableTemp()
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    when (it) {
                        is CitiesViewState.IsLoading -> {
                            loading(flag = it.isLoading)
                        }
                        is CitiesViewState.NavToHistoryScreen -> {
                            findNavController().navigateUp()
                        }
                        is CitiesViewState.NavToDetailsScreen -> {
                            findNavController().navigateUp()
                        }
                        is CitiesViewState.Success -> {
                            loading(false)
                            if (it.response.isEmpty()) {
                                binding.emptyView.visibility = View.VISIBLE
                                binding.citiesList.visibility = View.GONE
                            } else {
                                binding.emptyView.visibility = View.GONE
                                binding.citiesList.visibility = View.VISIBLE
                                adapter = CitiesAdapter(this@CitiesFragment)
                                binding.citiesList.layoutManager =
                                    LinearLayoutManager(requireContext())
                                binding.citiesList.adapter = adapter
                                adapter.submitList(it.response)
                                binding.citiesList.adapter = adapter
                            }

                        }
                        is CitiesViewState.Error -> {
                            loading(false)
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        }
                        is CitiesViewState.Init -> {
                            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                                // load if not loaded
                                viewModel.intentChannel.trySend(CitiesIntent.LoadCities)
                            }
                        }
                    }
                }
            }

            repeatOnLifecycle(Lifecycle.State.DESTROYED) {
                viewModel.clear()
            }
        }
    }

    override fun online() {
        // update UI
        if(!isOnline){
            isOnline = true
            showSnack("Online back.")
        }
    }

    override fun offline() {
        // update UI
        isOnline = false
        showSnack("You are offline.")
    }

    private fun loading(flag: Boolean) {
        if (flag) {
            binding.citiesList.visibility = View.INVISIBLE
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.citiesList.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onItemClick(item: City) {
        if(!isOnline){
            showSnack("You are offline.")
            return
        }
        val dest = CitiesFragmentDirections.actionFragmentCitiesToFragmentDetails(item, null)
        findNavController().safeNavigate(dest)
    }

    override fun onInfoClick(cityId: Long, cityName: String) {
        val dest =
            CitiesFragmentDirections.actionFragmentCitiesToFragmentWeatherHistory(cityId, cityName)
        findNavController().safeNavigate(dest)
    }

    override fun onAddClicked(response: CityResponse) {
        Toast.makeText(requireContext(), "This City is saved successfully.", Toast.LENGTH_SHORT)
            .show()
    }



}