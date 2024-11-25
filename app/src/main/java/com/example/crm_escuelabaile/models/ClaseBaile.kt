package com.example.crm_escuelabaile.models

data class ClaseBaile(
    val aula: String = "",
    val horario: List<String> = emptyList(),
    val nombre: String = "",
    val precio: Double = 0.0,
    val profesor: String = ""
)