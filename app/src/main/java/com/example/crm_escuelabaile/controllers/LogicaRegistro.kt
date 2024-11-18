package com.example.crm_escuelabaile.controllers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crm_escuelabaile.models.EstadoInicioSesion
import com.example.crm_escuelabaile.models.EstadoRegistro
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LogicaRegistro : ViewModel() {

    val db = FirebaseFirestore.getInstance()
    val coleccion = "administradores"

    val auth: FirebaseAuth = Firebase.auth

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _DNI = MutableStateFlow("")
    val dni: StateFlow<String> = _DNI.asStateFlow()

    private val _nombreCompleto = MutableStateFlow("")
    val nombreCompleto: StateFlow<String> = _nombreCompleto.asStateFlow()

    private val _telefono = MutableStateFlow("")
    val telefono: StateFlow<String> = _telefono.asStateFlow()

    private val _estadoRegistro = MutableStateFlow(EstadoRegistro.INICIAL)
    val estadoRegistro: StateFlow<EstadoRegistro> = _estadoRegistro.asStateFlow()

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }

    fun onNameChange(newName: String) {
        _nombreCompleto.value = newName
    }

    fun onDniChange(newDni: String) {
        _DNI.value = newDni
    }

    fun onTelefonoChange(newTelefono: String) {
        _telefono.value = newTelefono
    }
    fun registrarUsuario() {
        //Creamos una corrutina especifica de viewModel con launch()
        viewModelScope.launch {
            _estadoRegistro.value = EstadoRegistro.CARGANDO
            if (_email.value.isNotBlank() && _password.value.isNotBlank() && _DNI.value.isNotBlank() && _telefono.value.isNotBlank() && _nombreCompleto.value.isNotBlank()) {
                try {
                    val result = auth.createUserWithEmailAndPassword(_email.value, _password.value).await()
                    val user = result.user
                    if (user != null) {
                        val datosRegistro = hashMapOf(
                            "email" to _email.value,
                            "nombre" to _nombreCompleto.value,
                            "dni" to _DNI.value,
                            "telefono" to _telefono.value
                        )
                        db.collection(coleccion).document(user.uid).set(datosRegistro).await()
                        _estadoRegistro.value = EstadoRegistro.EXITO
                    } else {
                        _estadoRegistro.value = EstadoRegistro.ERROR
                    }
                } catch (e: Exception) {
                    _estadoRegistro.value = EstadoRegistro.ERROR
                }
            } else {
                _estadoRegistro.value = EstadoRegistro.ERROR
            }
        }
    }
}