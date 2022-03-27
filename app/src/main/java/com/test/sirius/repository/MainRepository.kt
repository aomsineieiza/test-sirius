package com.test.sirius.repository

import com.test.sirius.model.CityDataModel
import retrofit2.Response

interface MainRepository {
    suspend fun getCitiesList() : Response<MutableList<CityDataModel>>?
}