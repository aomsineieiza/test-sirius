package com.test.sirius.view.search

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.sirius.base.BaseFragment
import com.test.sirius.databinding.FragmentSearchBinding
import com.test.sirius.model.CityDataModel
import com.test.sirius.view.search.adapter.SearchResultsAdapter
import com.test.sirius.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.util.*

class SearchFragment : BaseFragment() {
    private lateinit var binding: FragmentSearchBinding
    private val cityList: MutableList<CityDataModel> = mutableListOf()
    private lateinit var searchResultsAdapter: SearchResultsAdapter
    private val viewModel: MainViewModel by sharedViewModel()
    private var timer: Timer? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        viewModel.getCitiesList()
        return binding.root
    }

    override fun setObserver() {
        with(binding) {
            viewModel.getCitiesListResponseModel.observe(viewLifecycleOwner) { result ->
                result.let { data ->
                    clearCityList()
                    cityList.addAll(data)
                    rvSearchResults.adapter?.notifyItemRangeChanged(0, cityList.size)
                    rvSearchResults.visibility = View.VISIBLE
                }
            }
            viewModel.searchCitiesListResponseModel.observe(viewLifecycleOwner) { result ->
                result.let { data ->
                    clearCityList()
                    cityList.addAll(data)
                    rvSearchResults.adapter?.notifyItemRangeChanged(0, cityList.size)
                    rvSearchResults.visibility = View.VISIBLE
                }
            }
            viewModel.loadingSearchCityDialogLiveData.observe(viewLifecycleOwner) { isShow ->
                if (isShow) {
                    binding.toolbar.progressBarSearchCity.visibility = View.VISIBLE
                } else {
                    binding.toolbar.progressBarSearchCity.visibility = View.GONE
                }
            }
        }

    }

    override fun setupView() {
        with(binding) {
            toolbar.etSearch.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int,
                ) {

                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int,
                ) {
                    if (timer != null) {
                        timer?.cancel()
                    }
                }

                override fun afterTextChanged(s: Editable?) {
                    timer = Timer()
                    timer?.schedule(object : TimerTask() {
                        override fun run() {
                            activity?.runOnUiThread {
                                with(binding) {
                                    rvSearchResults.visibility = View.GONE
                                    clearCityList()
                                }
                            }
                            viewModel.searchCitiesList(s.toString())
                        }
                    }, 800)
                }
            })

            searchResultsAdapter = SearchResultsAdapter(cityList)
            searchResultsAdapter.onItemClick = {
                navigateToMapLocation(it)
            }
            rvSearchResults.adapter = searchResultsAdapter
        }
    }

    private fun navigateToMapLocation(cityDataModel: CityDataModel) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data =
            Uri.parse("https://www.google.com/maps/search/?api=1&query=${cityDataModel.coord?.lat},${cityDataModel.coord?.lon}")
        startActivity(intent)
    }

    private fun clearCityList() {
        with(binding) {
            cityList.clear()
            rvSearchResults.adapter?.notifyDataSetChanged()
        }
    }

    companion object {
        fun newInstance() = SearchFragment()
    }
}