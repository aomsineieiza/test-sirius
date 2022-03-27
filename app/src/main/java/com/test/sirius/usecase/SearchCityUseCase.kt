package com.test.sirius.usecase

import com.test.sirius.base.CoroutinesUseCase
import com.test.sirius.model.CityDataModel
import com.test.sirius.network.Result
import com.test.sirius.repository.SearchRepositoryImplement
import java.lang.Exception

class SearchCityUseCase(private val searchRepositoryImplement: SearchRepositoryImplement) :
    CoroutinesUseCase<String, MutableList<CityDataModel>>() {
    override suspend fun execute(parameter: String): Result<MutableList<CityDataModel>> {
        val response = searchRepositoryImplement.searchCity(parameter)
        return try {
            response?.body()?.let {
                Result.Success(it)
            } ?: Result.Error("")
        } catch (e: Exception) {
            Result.Error("")
        }
    }
}