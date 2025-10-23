package com.cibertec.asistentememoria.api

import com.cibertec.asistentememoria.model.UserRequestLogin
import com.cibertec.asistentememoria.model.UserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiLoginService {

    @POST("login")
    fun loginUser(@Body user: UserRequestLogin): Call<UserResponse>
}