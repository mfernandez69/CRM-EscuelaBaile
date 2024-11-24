package com.example.crm_escuelabaile.controllers

import android.util.Log
import androidx.compose.material3.DrawerValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import com.example.crm_escuelabaile.models.Administrador
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class LogicaMenu : ViewModel() {
    val auth: FirebaseAuth = Firebase.auth
    private val _drawerState = mutableStateOf(DrawerValue.Closed)
    val drawerState: State<DrawerValue> = _drawerState

    private val _adminData = MutableStateFlow(Administrador())
    val adminData: StateFlow<Administrador> = _adminData.asStateFlow()

    fun openDrawer() {
        _drawerState.value = DrawerValue.Open
    }

    fun closeDrawer() {
        _drawerState.value = DrawerValue.Closed
    }

    fun recogerDatosAdmin() {
        viewModelScope.launch {
            val db = FirebaseFirestore.getInstance()
            val user = auth.currentUser
            if (user != null) {
                val email = user.email
                if (email != null) {
                    //obtenermos el admincuyo email coincida con el email con el que haya iniciado sesion
                    val query = db.collection("administradores").whereEqualTo("email", email).limit(1)
                    query.get().addOnSuccessListener { querySnapshot ->
                        if (!querySnapshot.isEmpty) {
                            val document = querySnapshot.documents[0]
                            val admin = Administrador(
                                nombre = document.getString("nombre") ?: "",
                                email = document.getString("email") ?: ""
                            )
                            _adminData.value = admin
                            Log.d("LogicaMenu", "Admin obtenido: Nombre=${admin.nombre}, Email=${admin.email}")
                        } else {
                            Log.d("LogicaMenu", "No se encontró el documento del administrador para el email: $email")
                        }
                    }.addOnFailureListener { e ->
                        Log.e("LogicaMenu", "Error al obtener datos del administrador", e)
                    }
                } else {
                    Log.d("LogicaMenu", "El usuario no tiene un email asociado")
                }
            } else {
                Log.d("LogicaMenu", "Usuario no autenticado")
            }
        }
    }
}