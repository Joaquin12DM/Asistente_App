package com.cibertec.asistentememoria.controller



import com.cibertec.asistentememoria.api.ApiSituacionMedicaService
import com.cibertec.asistentememoria.api.RetrofitClient
import com.cibertec.asistentememoria.model.SituacionMedicaRequest
import com.cibertec.asistentememoria.model.SituacionMedicaResponse
import retrofit2.Call

class SItuacionMedicaController {

    private val apiSituMedica: ApiSituacionMedicaService = RetrofitClient.apiSituacionMedica

    fun createSituMedica(situMedica: SituacionMedicaRequest): Call<SituacionMedicaResponse> {
        return apiSituMedica.createSituacionMedica(situMedica)
    }

    fun obtenerSituMedica(userId: Int): Call<SituacionMedicaResponse>{
        return apiSituMedica.obtenerSituacionMedica(userId)
    }
}