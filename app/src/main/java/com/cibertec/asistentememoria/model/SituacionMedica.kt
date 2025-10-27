package com.cibertec.asistentememoria.model

data class SituacionMedicaRequest(
    val usuario : Int,
    val condicion_medica: String,
    val alergias: String
)

data class SituacionMedicaResponse(
    val id: Int,
    val usuario : Int,
    val condicion_medica: String,
    val alergias: String
)
