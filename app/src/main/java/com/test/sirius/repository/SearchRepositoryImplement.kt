package com.test.sirius.repository

import com.test.sirius.model.CityDataModel
import com.test.sirius.service.Service
import retrofit2.Response

class SearchRepositoryImplement(
    private val service: Service,
) : SearchRepository {
    override suspend fun searchCity(parameter: String): Response<MutableList<CityDataModel>> {
        return service.getCityList()
    }

//    private fun getCityList(): MutableList<CityDataModel> {
//        val jsonFileString = getJsonDataFromAsset(context, "cities.json")
//        val gson = Gson()
//        val listCityType = object : TypeToken<MutableList<CityDataModel>>() {}.type
//
//        return gson.fromJson(jsonFileString, listCityType)
//    }

//    private fun searchCityFromList(keyword: String): MutableList<CityDataModel> {
//        if (keyword.isNotEmpty()) {
//            var cityList = getCityList()
//            cityList = cityList.filter {
//                if (keyword.length <= it.name?.length ?: 0) {
//                    val subtitle = it.name?.subSequence(0, keyword.length) ?: ""
//                    subtitle.contains(keyword, ignoreCase = true)
//                } else {
//                    false
//                }
//            } as MutableList<CityDataModel>
//            cityList.sortWith(compareBy(String.CASE_INSENSITIVE_ORDER) { it.country ?: "" })
//
//            return cityList
//        } else {
//            return getCityList()
//        }
//    }


}