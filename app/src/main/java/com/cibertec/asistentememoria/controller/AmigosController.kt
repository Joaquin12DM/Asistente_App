package com.cibertec.asistentememoria.controller

import com.cibertec.asistentememoria.api.ApiAmigosService
import com.cibertec.asistentememoria.api.RetrofitClient
import com.cibertec.asistentememoria.model.AmigosRequest
import com.cibertec.asistentememoria.model.AmigosResponse
import retrofit2.Call
import retrofit2.http.Path

class AmigosController {

    private val apiAmigos: ApiAmigosService = RetrofitClient.apiAmigos

    fun createAmigo(amigo: AmigosRequest): Call<AmigosResponse> {
        return apiAmigos.createAmigo(amigo)
    }

    fun obtenerAmigos(userId: Int): Call<List<AmigosResponse>>{
        return apiAmigos.obtenerAmigos(userId)
    }

    fun deleteAmigos(amigosId: Int): Call<Void>{
        return apiAmigos.deleteAmigos(amigosId)
    }
}