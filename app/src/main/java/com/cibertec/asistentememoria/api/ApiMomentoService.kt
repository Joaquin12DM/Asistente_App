package com.cibertec.asistentememoria.api

import com.cibertec.asistentememoria.model.MomentoRequest
import com.cibertec.asistentememoria.model.MomentoResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiMomentoService {

    @POST("momentos")
    fun createMomento(@Body user: MomentoRequest): Call<MomentoResponse>

    @GET("momentos/usuario/{usuarioId}")
    fun obtenerMomentos(@Path("usuarioId") userId: Int): Call<List<MomentoResponse>>

    @DELETE("momentos/{id}")
    fun deleteMomento(@Path("id") momentoId: Int): Call<Void>



}