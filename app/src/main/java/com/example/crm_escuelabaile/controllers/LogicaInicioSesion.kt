package com.example.crm_escuelabaile.controllers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crm_escuelabaile.models.EstadoInicioSesion
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LogicaInicioSesion : ViewModel() {
    val auth: FirebaseAuth = Firebase.auth

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _estadoInicioSesion = MutableStateFlow(EstadoInicioSesion.INICIAL)
    val estadoInicioSesion: StateFlow<EstadoInicioSesion> = _estadoInicioSesion.asStateFlow()

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }
    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }
    fun iniciarSesion() {
        viewModelScope.launch {
            _estadoInicioSesion.value = EstadoInicioSesion.CARGANDO
            if (_email.value.isNotBlank() && _password.value.isNotBlank()) {
                try {
                    val result = auth.signInWithEmailAndPassword(_email.value, _password.value).await()
                    _estadoInicioSesion.value = if (result.user != null) EstadoInicioSesion.EXITO else EstadoInicioSesion.ERROR
                } catch (e: Exception) {
                    _estadoInicioSesion.value = EstadoInicioSesion.ERROR
                }
            } else {
                _estadoInicioSesion.value = EstadoInicioSesion.ERROR
            }
        }
    }
}