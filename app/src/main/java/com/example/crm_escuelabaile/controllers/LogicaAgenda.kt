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
                Log.d("LogicaAgenda", "Tareas:${tareasObtenidas}")
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
                var query = coleccion.whereEqualTo("email", email)
                _fechaSeleccionada.value?.let { fecha ->
                    // Convertir el Timestamp a ZonedDateTime para manejar correctamente la zona horaria
                    val zonedDateTime = fecha.toDate().toInstant().atZone(ZoneId.systemDefault())
                    val inicioDia = zonedDateTime.withHour(0).withMinute(0).withSecond(0).withNano(0)
                    val finDia = zonedDateTime.withHour(23).withMinute(59).withSecond(59).withNano(999999999)

                    Log.e("LogicaAgenda", "Inicio dia: $inicioDia")
                    Log.e("LogicaAgenda", "Fin dia: $finDia")
                    Log.e("LogicaAgenda", "Tiempo seleccionado: ${_fechaSeleccionada.value}")

                    // Encadenar correctamente las condiciones de la consulta
                    query = query.whereGreaterThanOrEqualTo("fecha", Timestamp(inicioDia.toEpochSecond(), inicioDia.nano))
                        .whereLessThanOrEqualTo("fecha", Timestamp(finDia.toEpochSecond(), finDia.nano))
                }

                val querySnapshot = query.get().await()
                return querySnapshot.toObjects(Tarea::class.java)
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