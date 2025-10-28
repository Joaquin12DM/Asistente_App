package com.cibertec.asistentememoria.api

import com.cibertec.asistentememoria.model.InformacionPersonalRequest
import com.cibertec.asistentememoria.model.InformacionPersonalResponse
import com.cibertec.asistentememoria.model.SituacionMedicaRequest
import com.cibertec.asistentememoria.model.SituacionMedicaResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiSituacionMedicaService {

    @POST("situacionMedica")
    fun createSituacionMedica(@Body sitMedica: SituacionMedicaRequest): Call<SituacionMedicaResponse>

    @GET("situacionMedica/usuario/{usuarioId}")
    fun obtenerSituacionMedica(@Path("usuarioId") userId: Int): Call<SituacionMedicaResponse>

    @DELETE("situacionMedica/{id}")
    fun deleteSituacionMedica(@Path("id") situacionMedicaId: Int): Call<Void>
}