package com.cibertec.asistentememoria.api

import com.cibertec.asistentememoria.model.AmigosRequest
import com.cibertec.asistentememoria.model.AmigosResponse
import com.cibertec.asistentememoria.model.SituacionMedicaRequest
import com.cibertec.asistentememoria.model.SituacionMedicaResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiAmigosService {

    @POST("amigos")
    fun createAmigo(@Body amigo: AmigosRequest): Call<AmigosResponse>

    @GET("amigos/usuario/{usuarioId}")
    fun obtenerAmigos(@Path("usuarioId") userId: Int): Call<List<AmigosResponse>>
}