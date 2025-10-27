package com.cibertec.asistentememoria.model

data class UserRequest(
    val nombre: String = "",
    val apellido: String = "",
    val email: String = "",
    val contrasena: String = "",
    val fechaNacimiento: String = "",
)

data class UserRequestLogin(
    val email: String = "",
    val contrasena: String = ""
)

data class UserResponse(
    val id: Int = 0,
    val nombre: String = "",
    val apellido: String = "",
    val email: String = "",
    val contrasena: String = "",
    val fechaNacimiento: String = "",
    val telefono: String =""
)

data class UsuarioRequestEdit(
    val nombre: String = "",
    val apellido: String = "",
    val telefono: String? ="",
    val fechaNacimiento: String? = ""
)
