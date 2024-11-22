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
                val docRef = db.collection("administradores").document(user.uid)
                docRef.get().addOnSuccessListener { document ->
                    if (document != null) {
                        _adminData.value = Administrador(
                            nombre = document.getString("nombre") ?: "",
                            email = document.getString("email") ?: "",

                        )
                        Log.d("LogicaMenu", "Admin obtenido de Firestore: ${adminData}")
                    }
                }
            }
        }
    }
}