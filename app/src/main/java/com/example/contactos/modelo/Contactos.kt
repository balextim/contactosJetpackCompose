package com.example.contactos.modelo

import java.io.Serializable

data class Contactos(
    val nombre: String?,
    val apellido: String?,
    val telefono: String?,
    val correo: String?,
    val descripcion: String?,

):Serializable
