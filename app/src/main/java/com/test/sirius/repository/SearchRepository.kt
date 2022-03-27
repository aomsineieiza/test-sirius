package com.test.sirius.repository

import com.test.sirius.model.CityDataModel
import retrofit2.Response

interface SearchRepository {
    suspend fun searchCity(parameter: String) : Response<MutableList<CityDataModel>>?
}