package com.example.crm_escuelabaile.models

import com.google.firebase.Timestamp

data class Tarea(
    val descripcion: String = "",
    val fecha: Timestamp? = null,
    val nombre: String = "",
    val ubicacion: String = "",
    val email: String = ""
)
