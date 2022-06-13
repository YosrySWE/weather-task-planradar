package com.planradar.weatherapptask.presentation.features.search

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.planradar.weatherapptask.R
import com.planradar.weatherapptask.data.remote.dto.CityResponse
import com.planradar.weatherapptask.databinding.DialogSearchBinding
import com.planradar.weatherapptask.util.extensions.Keyboard
import com.planradar.weatherapptask.util.extensions.disableTemp
import com.planradar.weatherapptask.util.extensions.itsText
import com.planradar.weatherapptask.util.extensions.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SearchDialog : BottomSheetDialogFragment() {

    companion object{
        fun newInstance(callback: Callback): SearchDialog{
            return SearchDialog().apply { this.callback = callback }
        }
    }
    private var _binding: DialogSearchBinding? = null
    private val binding get() = _binding!!

    var result : CityResponse? = null

    val viewModel: SearchViewModel by viewModels()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    var callback: Callback? = null

    interface Callback {
        fun onAddClicked(response: CityResponse)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout to use as dialog or embedded fragment
        _binding = DialogSearchBinding.inflate(LayoutInflater.from(context))


        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val window = dialog?.window
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        observe()

    }


    private fun observe() {
        binding.tvCityName.setOnClickListener { view ->
            if (binding.tvCityName.text == getString(R.string.no_result) || result == null) {
                return@setOnClickListener
            }
            binding.tvCityName.isEnabled = false
            Toast.makeText(requireContext(), "saving ...", Toast.LENGTH_SHORT).show()
            viewModel.intentChannel.trySend(SearchIntent.SaveCity(result!!))
            view.disableTemp()
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    when (it) {
                        is SearchViewState.IsLoading -> {
                            loading(flag = it.isLoading)
                        }
                        is SearchViewState.NavToCities -> {
                            callback?.onAddClicked(it.city)
                            viewModel.clear()
                            dismiss()
                        }
                        is SearchViewState.Success -> {
                            loading(false)
                            binding.tvCityName.show()
                            result = it.response

                            binding.tvCityName.text =
                                "${it.response.name}, ${it.response.sys.country}"

                        }
                        is SearchViewState.Error -> {
                            loading(false)
                            binding.tvCityName.isEnabled = true
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        }
                        is SearchViewState.Init -> {
                            binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
                                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                                    val key = binding.etSearch.itsText()
                                    if (key.isEmpty()) {
                                        Toast.makeText(
                                            requireContext(),
                                            "please, enter text to search",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        return@setOnEditorActionListener false
                                    }
                                    Keyboard.hideKeyboard(view)
                                    viewModel.intentChannel.trySend(SearchIntent.SearchCity(key))
                                }
                                true
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

    private fun loading(flag: Boolean) {
        if (flag) {
            binding.tvCityName.visibility = View.INVISIBLE
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.tvCityName.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE
        }
    }


}