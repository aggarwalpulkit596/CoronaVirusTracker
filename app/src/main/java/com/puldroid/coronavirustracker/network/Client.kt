package com.puldroid.coronavirustracker.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Client {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://services1.arcgis.com/0MSEUqKaxRlEPj5g/arcgis/rest/services/ncov_cases/FeatureServer/1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val api = retrofit.create(RestApi::class.java)
}