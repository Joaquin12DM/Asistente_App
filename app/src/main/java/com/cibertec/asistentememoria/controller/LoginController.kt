package com.cibertec.asistentememoria.controller

import com.cibertec.asistentememoria.api.ApiLoginService
import com.cibertec.asistentememoria.api.RetrofitClient
import com.cibertec.asistentememoria.model.UserRequestLogin
import com.cibertec.asistentememoria.model.UserResponse
import retrofit2.Call

class LoginController {

    private val apiLogin: ApiLoginService = RetrofitClient.apiLoginUser

    fun login(user: UserRequestLogin): Call<UserResponse> {
        return  apiLogin.loginUser(user)
    }
}