package com.example.crm_escuelabaile.models

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference

data class Alumno(
    val dni: String = "",
    val email: String = "",
    val estadoPago: Boolean = false,
    val fechaDeNacimiento: Timestamp? = null,
    val nombre: String = "",
    val clases: List<DocumentReference> = emptyList() // Referencias a otras colecciones
)