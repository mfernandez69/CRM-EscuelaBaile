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

    //Utilizamos StateFlow para manejar el estado de la UI de manera reactiva.
    //_email es una variable de tipo MutableStateFlow() ya que su valor puede variar con el tiempo
    private val _email = MutableStateFlow("")
    //.asStateFlow() permite que sean observadas desde fuera de la clase, pero no modificadas directamente
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    //Iniciamos el _estadoInicioSesion como inicial y en el view definimos que es una cadena vacia
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
    fun cerrarSesion(){
        auth.signOut()
    }
}