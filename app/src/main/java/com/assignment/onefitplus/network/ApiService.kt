package com.assignment.onefitplus.network

import com.assignment.onefitplus.constants.Constants
import com.assignment.onefitplus.pojo.CountryDetail
import com.google.gson.GsonBuilder
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    companion object{


        private val retrofitService by lazy {
            Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }

        fun getInstance():ApiService= retrofitService
    }

    @GET("json/cities.json")
    suspend fun getJsonRespondList():Response<List<CountryDetail>>
}