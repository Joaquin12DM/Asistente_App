package com.cibertec.asistentememoria.api

import com.cibertec.asistentememoria.model.UserRequest
import com.cibertec.asistentememoria.model.UserResponse
import com.cibertec.asistentememoria.model.UsuarioRequestEdit
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiUserService {

    @POST("usuarios")
    fun createUser(@Body user: UserRequest): Call<UserResponse>

    @GET("usuarios/{usuarioId}")
    fun obtenerUser(@Path("usuarioId")userId:Int): Call<UserResponse>

    @PUT("usuarios/{id}")
    fun updateUser(@Path("id") id: Int, @Body user: UsuarioRequestEdit): Call<UserResponse>


}