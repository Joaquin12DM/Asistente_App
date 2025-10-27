package com.cibertec.asistentememoria.controller

import com.cibertec.asistentememoria.api.ApiFamiliaresService
import com.cibertec.asistentememoria.api.ApiInformacionPersonalService
import com.cibertec.asistentememoria.api.RetrofitClient
import com.cibertec.asistentememoria.model.FamiliaresRequest
import com.cibertec.asistentememoria.model.FamiliaresResponse
import com.cibertec.asistentememoria.model.InformacionPersonalRequest
import com.cibertec.asistentememoria.model.InformacionPersonalResponse
import retrofit2.Call

class InformacionPersonalController {

    private val apiInfoPerso: ApiInformacionPersonalService = RetrofitClient.apiInformacionPersonal

    fun createInfoPerso(infoPerso: InformacionPersonalRequest): Call<InformacionPersonalResponse> {
        return apiInfoPerso.createInformacionPersonal(infoPerso)
    }

    fun obtenerInfoPerso(userId: Int): Call<InformacionPersonalResponse> {
        return apiInfoPerso.obtenerInformacionPersonal(userId)
    }
}