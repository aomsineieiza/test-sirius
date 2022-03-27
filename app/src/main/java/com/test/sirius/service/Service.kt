package com.test.sirius.service

import com.test.sirius.model.CityDataModel
import retrofit2.Response
import retrofit2.http.GET

interface Service {
    @GET("/SiriusAndroid/android-assignment/master/cities.json")
    suspend fun getCitiesList(): Response<MutableList<CityDataModel>>
}