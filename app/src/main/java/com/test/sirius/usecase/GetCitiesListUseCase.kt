package com.test.sirius.usecase

import com.test.sirius.base.CoroutinesUseCase
import com.test.sirius.model.CityDataModel
import com.test.sirius.network.Result
import com.test.sirius.repository.MainRepositoryImplement
import java.lang.Exception

class GetCitiesListUseCase(private val mainRepositoryImplement: MainRepositoryImplement) :
    CoroutinesUseCase<Any?, MutableList<CityDataModel>>() {
    override suspend fun execute(parameter: Any?): Result<MutableList<CityDataModel>> {
        val response = mainRepositoryImplement.getCitiesList()
        return try {
            response?.body()?.let {
                Result.Success(it)
            } ?: Result.Error("")
        } catch (e: Exception) {
            Result.Error("")
        }
    }
}