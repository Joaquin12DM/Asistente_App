package com.cibertec.asistentememoria.controller

import com.cibertec.asistentememoria.api.ApiFamiliaresService
import com.cibertec.asistentememoria.api.RetrofitClient
import com.cibertec.asistentememoria.model.FamiliaresRequest
import com.cibertec.asistentememoria.model.FamiliaresResponse
import retrofit2.Call

class FamiliaresController {

    private val apiFamilia: ApiFamiliaresService = RetrofitClient.apiFamiliares

    fun createFamiliar(familiar: FamiliaresRequest): Call<FamiliaresResponse> {
        return apiFamilia.createfamiliar(familiar)
    }

    fun obtenerfamiliar(userId: Int): Call<List<FamiliaresResponse>>{
        return apiFamilia.obtenerFamiliar(userId)
    }

    fun deleteFamiliar(familiaresId: Int): Call<Void>{
        return apiFamilia.deleteFamiliares(familiaresId)
    }



}