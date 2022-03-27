package com.test.sirius.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.sirius.model.CityDataModel
import com.test.sirius.usecase.GetCitiesListUseCase
import com.test.sirius.utils.SingleLiveEvent
import com.test.sirius.network.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val getCitiesListUseCase: GetCitiesListUseCase,
) : ViewModel() {
    val getCitiesListResponseModel: SingleLiveEvent<MutableList<CityDataModel>> = SingleLiveEvent()
    val searchCitiesListResponseModel: SingleLiveEvent<MutableList<CityDataModel>> = SingleLiveEvent()
    var loadingSearchCityDialogLiveData: SingleLiveEvent<Boolean> = SingleLiveEvent()

    fun getCitiesList() {
        loadingSearchCityDialogLiveData.postValue(true)

        viewModelScope.launch(Dispatchers.IO) {
            when (val result = getCitiesListUseCase.execute("")) {
                is Result.Success -> {
                    loadingSearchCityDialogLiveData.postValue(false)
                    getCitiesListResponseModel.postValue(result.data)
                }
                is Result.Error -> {
                    loadingSearchCityDialogLiveData.postValue(false)
                }
                else -> {
                    loadingSearchCityDialogLiveData.postValue(false)
                }
            }
        }
    }

    fun searchCitiesList(
        keyword: String,
    ) {
        loadingSearchCityDialogLiveData.postValue(true)

        if (keyword.isNotEmpty()) {
            val searchResult = getCitiesListResponseModel.value?.filter {
                if (keyword.length <= it.name?.length ?: 0) {
                    val subtitle = it.name?.subSequence(0, keyword.length) ?: ""
                    subtitle.contains(keyword, ignoreCase = true)
                } else {
                    false
                }
            } as MutableList<CityDataModel>
            searchResult.sortWith(compareBy(String.CASE_INSENSITIVE_ORDER) { it.name ?: "" })
            searchResult.sortWith(compareBy(String.CASE_INSENSITIVE_ORDER) { it.country ?: "" })

            searchCitiesListResponseModel.postValue(searchResult)
            loadingSearchCityDialogLiveData.postValue(false)
        } else {
            searchCitiesListResponseModel.postValue(getCitiesListResponseModel.value)
            loadingSearchCityDialogLiveData.postValue(false)
        }
    }
}