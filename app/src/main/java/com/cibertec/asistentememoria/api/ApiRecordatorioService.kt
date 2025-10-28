package com.cibertec.asistentememoria.api

import com.cibertec.asistentememoria.model.RecordatorioRequest
import com.cibertec.asistentememoria.model.RecordatorioResponse
import com.cibertec.asistentememoria.model.RecordatorioTipoRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiRecordatorioService {


    @POST("recordatorios")
    fun createRecordatorio(@Body recordatorio: RecordatorioRequest): Call<RecordatorioResponse>

    @GET("recordatorios/hoy/usuario/{usuarioId}")
    fun obtenerRecordatoriosHoy(@Path("usuarioId") userId: Int): Call<List<RecordatorioResponse>>


    @POST("recordatorios/buscar")
    fun buscarPorTipo(@Body recordatorio: RecordatorioTipoRequest): Call<List<RecordatorioResponse>>

    @DELETE("recordatorios/{id}")
    fun deleteRecordatorio(@Path("id") recordatorioId: Int): Call<Void>


}