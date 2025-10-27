package com.cibertec.asistentememoria.controller

import com.cibertec.asistentememoria.api.ApiFamiliaresService
import com.cibertec.asistentememoria.api.ApiMomentoService
import com.cibertec.asistentememoria.api.RetrofitClient
import com.cibertec.asistentememoria.model.FamiliaresRequest
import com.cibertec.asistentememoria.model.FamiliaresResponse
import com.cibertec.asistentememoria.model.MomentoRequest
import com.cibertec.asistentememoria.model.MomentoResponse
import retrofit2.Call

class FamiliaresController {

    private val apiFamilia: ApiFamiliaresService = RetrofitClient.apiFamiliares

    fun createFamiliar(familiar: FamiliaresRequest): Call<FamiliaresResponse> {
        return apiFamilia.createfamiliar(familiar)
    }

    fun obtenerfamiliar(userId: Int): Call<List<FamiliaresResponse>>{
        return apiFamilia.obtenerFamiliar(userId)
    }



}