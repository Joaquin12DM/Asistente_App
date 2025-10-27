package com.cibertec.asistentememoria.controller

import com.cibertec.asistentememoria.api.ApiUserService
import com.cibertec.asistentememoria.api.RetrofitClient
import com.cibertec.asistentememoria.model.UserRequest
import com.cibertec.asistentememoria.model.UserResponse
import com.cibertec.asistentememoria.model.UsuarioRequestEdit
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Path

class UserController {

    private val apiUser: ApiUserService = RetrofitClient.apiUserService


    fun createUser(user: UserRequest): Call<UserResponse> {
        return apiUser.createUser(user)
    }
    fun obtenerUser(userId:Int): Call<UserResponse>{
        return apiUser.obtenerUser(userId)
    }


    fun updateUser( id: Int, user: UsuarioRequestEdit): Call<UserResponse>{
        return apiUser.updateUser(id,user)
    }

}