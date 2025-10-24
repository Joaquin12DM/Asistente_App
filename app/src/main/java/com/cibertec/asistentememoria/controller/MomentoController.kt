package com.cibertec.asistentememoria.controller

import com.cibertec.asistentememoria.api.ApiMomentoService
import com.cibertec.asistentememoria.api.RetrofitClient
import com.cibertec.asistentememoria.model.MomentoRequest
import com.cibertec.asistentememoria.model.MomentoResponse
import retrofit2.Call
import retrofit2.http.Path

class MomentoController {

    private val apiMomento: ApiMomentoService = RetrofitClient.apiMomento

    fun createMomento(momento: MomentoRequest): Call<MomentoResponse> {
        return apiMomento.createMomento(momento)
    }

    fun obtenerMomentos(userId: Int): Call<List<MomentoResponse>>{
        return apiMomento.obtenerMomentos(userId)
    }

}