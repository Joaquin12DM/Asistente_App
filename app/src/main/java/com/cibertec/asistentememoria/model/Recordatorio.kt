package com.cibertec.asistentememoria.model

data class RecordatorioRequest(
    val usuario: Int,
    val tipo: String,
    val titulo: String,
    val descripcion: String,
    val fechaHora: String,
    val frecuencia: String
)

data class RecordatorioResponse(
    val id: Int,
    val usuarioId: Int,
    val usuarioNombre: String?,
    val tipo: String,
    val titulo: String,
    val descripcion: String,
    val fechaHora: String,
    val frecuencia: String
)

data class RecordatorioTipoRequest(
    val usuarioId: Int,
    val tipo: String
)
