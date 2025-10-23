package com.cibertec.asistentememoria.api

import com.cibertec.asistentememoria.model.UserRequest
import com.cibertec.asistentememoria.model.UserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiUserService {

    @POST("usuarios")
    fun createUser(@Body user: UserRequest): Call<UserResponse>


}