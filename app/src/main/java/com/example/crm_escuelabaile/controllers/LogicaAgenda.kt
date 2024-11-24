package com.example.crm_escuelabaile.controllers

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crm_escuelabaile.models.Notificacion
import com.example.crm_escuelabaile.models.Tarea
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

import com.google.firebase.Timestamp
import java.time.ZoneId

class LogicaAgenda : ViewModel() {
    val auth: FirebaseAuth = Firebase.auth

    private val _tareasDelDia = MutableStateFlow<List<Tarea>>(emptyList())
    val tareasDelDia: StateFlow<List<Tarea>> = _tareasDelDia

    private val _fechaSeleccionada = MutableStateFlow<Timestamp?>(null)

    init {
        Log.d("LogicaAgenda", "Inicializando ViewModel")
        cargarTareas()
    }

    fun setFechaSeleccionada(fecha: Timestamp) {
        _fechaSeleccionada.value = fecha
        cargarTareas()
    }

    fun cargarTareas() {
        viewModelScope.launch {
            try {
                val tareasObtenidas = getTareas()
                _tareasDelDia.value = tareasObtenidas
            } catch (e: Exception) {
                Log.e("LogicaAgenda", "Error al cargar las tareas", e)
            }
        }
    }

    private suspend fun getTareas(): List<Tarea> {
        val user = auth.currentUser
        val email = user?.email
        val db = FirebaseFirestore.getInstance()
        val coleccion = db.collection("tarea")

        return try {
            if (email != null) {
                val query = coleccion.whereEqualTo("email", email)
                _fechaSeleccionada.value?.let { fecha ->
                    // Crear un rango de fechas para el día seleccionado
                    val inicioDia = fecha.toDate().toInstant().atZone(ZoneId.systemDefault()).withHour(0).withMinute(0).withSecond(0).toInstant()
                    val finDia = fecha.toDate().toInstant().atZone(ZoneId.systemDefault()).withHour(23).withMinute(59).withSecond(59).toInstant()

                    query.whereGreaterThanOrEqualTo("fecha", Timestamp(inicioDia.epochSecond, 0))
                        .whereLessThanOrEqualTo("fecha", Timestamp(finDia.epochSecond, 999999999))
                }
                val querySnapshot = query.get().await()
                querySnapshot.toObjects(Tarea::class.java)
            } else {
                Log.e("LogicaAgenda", "Email del usuario es null")
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("LogicaAgenda", "Error al obtener tareas de Firestore", e)
            emptyList()
        }
    }
}