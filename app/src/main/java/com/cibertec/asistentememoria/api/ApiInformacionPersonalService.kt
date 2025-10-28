package com.cibertec.asistentememoria.api

import com.cibertec.asistentememoria.model.InformacionPersonalRequest
import com.cibertec.asistentememoria.model.InformacionPersonalResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiInformacionPersonalService {

    @POST("informacionPersonal")
    fun createInformacionPersonal(@Body infoPerso: InformacionPersonalRequest): Call<InformacionPersonalResponse>

    @GET("informacionPersonal/usuario/{usuarioId}")
    fun obtenerInformacionPersonal(@Path("usuarioId") userId: Int): Call<InformacionPersonalResponse>

    @DELETE("informacionPersonal/{id}")
    fun deleteInformacionPersonal(@Path("id") informacionPersonalId: Int): Call<Void>
}