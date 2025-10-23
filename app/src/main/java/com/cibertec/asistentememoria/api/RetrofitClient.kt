package com.cibertec.asistentememoria.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "https://app-spring2025.azurewebsites.net/api/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(RetrofitClient.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiUserService: ApiUserService by lazy {
        RetrofitClient.retrofit.create(ApiUserService::class.java)
    }

    val apiLoginUser: ApiLoginService by lazy {
        RetrofitClient.retrofit.create(ApiLoginService::class.java)
    }

    val apiRecordatorio: ApiRecordatorioService by lazy {
        RetrofitClient.retrofit.create(ApiRecordatorioService::class.java)
    }

}