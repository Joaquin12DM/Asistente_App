package com.cibertec.asistentememoria.controller

import com.cibertec.asistentememoria.api.ApiUserService
import com.cibertec.asistentememoria.api.RetrofitClient
import com.cibertec.asistentememoria.model.UserRequest
import com.cibertec.asistentememoria.model.UserResponse
import retrofit2.Call

class UserController {

    private val apiUser: ApiUserService = RetrofitClient.apiUserService


    fun createUser(user: UserRequest): Call<UserResponse> {
        return apiUser.createUser(user)
    }

}