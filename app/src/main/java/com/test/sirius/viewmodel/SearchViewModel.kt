package com.test.sirius.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.sirius.model.CityDataModel
import com.test.sirius.usecase.SearchCityUseCase
import com.test.sirius.utils.SingleLiveEvent
import com.test.sirius.network.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchCityUseCase: SearchCityUseCase,
) : ViewModel() {
    val searchListResponseModel: SingleLiveEvent<MutableList<CityDataModel>> = SingleLiveEvent()
    var loadingSearchCouponDialogLiveData: SingleLiveEvent<Boolean> = SingleLiveEvent()

    fun searchCity(search: String) {
        loadingSearchCouponDialogLiveData.postValue(true)

        viewModelScope.launch(Dispatchers.IO) {
            when (val result = searchCityUseCase.execute(search)) {
                is Result.Success -> {
                    loadingSearchCouponDialogLiveData.postValue(false)
                    searchListResponseModel.postValue(result.data)
                }
                is Result.Error -> {
                    loadingSearchCouponDialogLiveData.postValue(false)
                }
                else -> {
                    loadingSearchCouponDialogLiveData.postValue(false)
                }
            }
        }
    }
}