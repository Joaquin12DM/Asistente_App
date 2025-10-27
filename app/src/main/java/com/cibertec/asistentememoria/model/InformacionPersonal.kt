package com.cibertec.asistentememoria.model

data class InformacionPersonalRequest(
    val usuario : Int,
    val hobbies: String,
    val comidaFavorita: String,
    val musicaFavorita: String,
    val colorFavorito: String
)

data class InformacionPersonalResponse(
    val id: Int,
    val usuario : Int,
    val hobbies: String,
    val comidaFavorita: String,
    val musicaFavorita: String,
    val colorFavorito: String
)
