package com.cibertec.asistentememoria.api

import com.cibertec.asistentememoria.model.FamiliaresRequest
import com.cibertec.asistentememoria.model.FamiliaresResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiFamiliaresService {

    @POST("familiares")
    fun createfamiliar(@Body amigo: FamiliaresRequest): Call<FamiliaresResponse>

    @GET("familiares/usuario/{usuarioId}")
    fun obtenerFamiliar(@Path("usuarioId") userId: Int): Call<List<FamiliaresResponse>>

    @DELETE("familiares/{id}")
    fun deleteFamiliares(@Path("id") familiaresId: Int): Call<Void>
}