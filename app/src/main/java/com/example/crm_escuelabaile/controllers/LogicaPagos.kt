package com.example.crm_escuelabaile.controllers

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crm_escuelabaile.models.Alumno
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LogicaPagos : ViewModel() {

    private val db = Firebase.firestore

    private val _AlumnosSinPagar = MutableStateFlow<List<Alumno>>(emptyList())
    val AlumnosSinPagar: StateFlow<List<Alumno>> = _AlumnosSinPagar

    private val _AlumnosQuePagaron = MutableStateFlow<List<Alumno>>(emptyList())
    val AlumnosQuePagaron: StateFlow<List<Alumno>> = _AlumnosQuePagaron

    private val _cantidadNoPagos = MutableStateFlow(0)
    val cantidadNoPagos: StateFlow<Int> = _cantidadNoPagos.asStateFlow()

    init {
        Log.d("LogicaPagos", "Inicializando ViewModel")
        cargarAlumnos()
    }

    private fun cargarAlumnos() {
        viewModelScope.launch {
            try {
                // Suponiendo que tienes una función para obtener alumnos desde Firestore
                val alumnosObtenidos = getAlumnosFromFirestore()
                _AlumnosSinPagar.value = alumnosObtenidos.filter { !it.estadoPago }
                _AlumnosQuePagaron.value = alumnosObtenidos.filter { it.estadoPago }
                _cantidadNoPagos.value = _AlumnosSinPagar.value.size
                Log.d("LogicaPagos", "Alumnos cargados: ${alumnosObtenidos.size}")
                Log.d("LogicaPagos", "Alumnos sin pagar: ${_cantidadNoPagos.value}")
            } catch (e: Exception) {
                Log.e("LogicaPagos", "Error al cargar alumnos", e)
            }
        }
    }

    private suspend fun getAlumnosFromFirestore(): List<Alumno> {
        return try {
            val snapshot = db.collection("alumnos").get().await()
            snapshot.documents.mapNotNull { it.toObject(Alumno::class.java) }
        } catch (e: Exception) {
            Log.e("LogicaPagos", "Error al obtener alumnos", e)
            emptyList()
        }
    }
}