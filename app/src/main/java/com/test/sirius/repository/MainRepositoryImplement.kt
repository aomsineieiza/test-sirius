package com.test.sirius.repository

import com.test.sirius.model.CityDataModel
import com.test.sirius.service.Service
import retrofit2.Response

class MainRepositoryImplement(
    private val service: Service,
) : MainRepository {
    override suspend fun getCitiesList(): Response<MutableList<CityDataModel>> {
        return service.getCitiesList()
    }
}