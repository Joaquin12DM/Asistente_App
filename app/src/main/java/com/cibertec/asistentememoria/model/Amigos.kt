package com.cibertec.asistentememoria.model

data class AmigosRequest(
    val usuario : Int,
    val nombre: String,
    val telefono: String
)

data class AmigosResponse(
    val id: Int,
    val usuario : Int,
    val nombre: String,
    val telefono: String
)
