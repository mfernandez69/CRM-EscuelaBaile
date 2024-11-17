package com.example.crm_escuelabaile.controllers

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class LogicaPantallaPrueba : ViewModel() {
    var contador by mutableStateOf(0)

    fun aumentarContador(){
        contador++
    }
}
