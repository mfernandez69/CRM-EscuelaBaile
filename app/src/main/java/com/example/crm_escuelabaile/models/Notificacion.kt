package com.example.crm_escuelabaile.models

import java.time.LocalDate

data class Notificacion(
    val titulo: String = "",
    val descripcion: String = "",
    val email: String = "",
    val telefono: String = "",
    val fecha: LocalDate? = null
)