package com.cibertec.asistentememoria.model

data class FamiliaresRequest(
    val usuario : Int,
    val nombre: String,
    val relacion: String,
    val telefono: String
)

data class FamiliaresResponse(
    val id: Int,
    val usuario : Int,
    val nombre: String,
    val relacion: String,
    val telefono: String
)
