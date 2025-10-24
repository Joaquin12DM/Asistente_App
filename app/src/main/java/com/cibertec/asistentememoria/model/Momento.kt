package com.cibertec.asistentememoria.model

data class MomentoRequest(
    val usuarioId: Int,
    val titulo: String ,
    val descripcion: String ,
    val fecha: String ,
    val personasPresentes: String = ""
)

data class MomentoResponse(
    val id: Int,
    val usuarioId: Long,
    val titulo: String,
    val descripcion: String,
    val fecha: String,
    val personasPresentes: String? = null
)


