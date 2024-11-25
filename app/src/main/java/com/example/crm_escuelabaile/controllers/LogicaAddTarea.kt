package com.example.crm_escuelabaile.controllers

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LogicaAddTarea : ViewModel(){
    private val _nombre = MutableStateFlow("")
    val nombre: StateFlow<String> get() = _nombre

    private val _descripcion = MutableStateFlow("")
    val descripcion: StateFlow<String> get() = _descripcion

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> get() = _email

    private val _ubicacion = MutableStateFlow("")
    val ubicacion: StateFlow<String> get() = _ubicacion

    fun updateNombre(value: String) {
        _nombre.value = value
    }

    fun updateDescripcion(value: String) {
        _descripcion.value = value
    }

    fun updateEmail(value: String) {
        _email.value = value
    }

    fun updateUbicacion(value: String) {
        _ubicacion.value = value
    }
}