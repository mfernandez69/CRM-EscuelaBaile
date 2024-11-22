package com.example.crm_escuelabaile.models

import com.google.firebase.Timestamp
import java.time.LocalDate

data class Notificacion(
    val id: String = "",
    val titulo: String = "",
    val descripcion: String = "",
    val email: String = "",
    val telefono: String = "",
    val fecha: Timestamp? = null,
    var leida :Boolean = false
)