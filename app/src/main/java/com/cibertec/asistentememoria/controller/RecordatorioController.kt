package com.cibertec.asistentememoria.controller

import com.cibertec.asistentememoria.api.ApiRecordatorioService
import com.cibertec.asistentememoria.api.RetrofitClient
import com.cibertec.asistentememoria.model.RecordatorioRequest
import com.cibertec.asistentememoria.model.RecordatorioResponse
import com.cibertec.asistentememoria.model.RecordatorioTipoRequest
import retrofit2.Call
import retrofit2.http.Path

class RecordatorioController {

    private val apiRecordatorio: ApiRecordatorioService = RetrofitClient.apiRecordatorio

    fun createRecordatorio(recordatorio: RecordatorioRequest): Call<RecordatorioResponse>{
        return apiRecordatorio.createRecordatorio(recordatorio)
    }

    fun buscarPorTipo(recordatorio: RecordatorioTipoRequest): Call<List<RecordatorioResponse>> {
        return apiRecordatorio.buscarPorTipo(recordatorio)
    }

    fun obtenerRecordatoriosHoy(userId: Int): Call<List<RecordatorioResponse>>{
        return apiRecordatorio.obtenerRecordatoriosHoy(userId)
    }

    fun deleteRecordatorio(recordatorioId: Int): Call<Void>{
        return apiRecordatorio.deleteRecordatorio(recordatorioId)
    }



}