package com.example.crm_escuelabaile.models

import com.google.firebase.Timestamp

data class Tarea(
    val descripción: String = "",
    val fecha: Timestamp? = null,
    val nombre: String = "",
    val ubicacion: String = "",
    val email: String = ""
)
