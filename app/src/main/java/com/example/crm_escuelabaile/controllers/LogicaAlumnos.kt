package com.example.crm_escuelabaile.controllers

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crm_escuelabaile.models.Administrador
import com.example.crm_escuelabaile.models.Alumno
import com.example.crm_escuelabaile.models.Tarea
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LogicaAlumnos: ViewModel() {
    val auth: FirebaseAuth = Firebase.auth

    private val _alumnos = MutableStateFlow<List<Alumno>>(emptyList())
    var alumnos: StateFlow<List<Alumno>> = _alumnos

    private val _alumnosFiltrados = MutableStateFlow<List<Alumno>>(emptyList())
    var alumnosFiltrados: StateFlow<List<Alumno>> = _alumnosFiltrados

    private val _nombreAlumno = MutableStateFlow("")
    val nombreAlumno: StateFlow<String> get() = _nombreAlumno.asStateFlow()

    val _alumnoMostrando = MutableStateFlow(Alumno())
    val alumnoMostrando: StateFlow<Alumno> = _alumnoMostrando.asStateFlow()

    fun updateNombreAlumno(value: String) {
        _nombreAlumno.value = value
    }

    fun setAlumnoMostrando(value: Alumno){
        _alumnoMostrando.value = value
    }
    
    fun getAlumnoMostrando(): Alumno{
        return _alumnoMostrando.value
    }

    init {
        Log.d("LogicaALumnos", "Inicializando ViewModel")
        cargarAlumnos()
    }


    fun cargarAlumnos() {
        viewModelScope.launch {
            try {
                val alumnosObtenidos = getAlumnos()
                _alumnos.value = alumnosObtenidos
                val alumnosPorFiltrar = _alumnos.value
                val alumnosFilter = alumnosPorFiltrar.filter { it.nombre.contains(nombreAlumno.value, true) }
                _alumnosFiltrados.value = alumnosFilter
                Log.d("LogicaAlumnos", "Alumnos:${alumnosObtenidos}")
            } catch (e: Exception) {
                Log.e("LogicaAlumnos", "Error al cargar los alumnos", e)
            }
        }
    }


    suspend fun getAlumnos(): List<Alumno> {
        val user = auth.currentUser
        val email = user?.email
        val db = FirebaseFirestore.getInstance()
        val coleccion = db.collection("alumnos")

        return try {
            run {
                Log.d("LogicaAlumnos", "Entra")
                val query = coleccion

                val querySnapshot = query.get().await()
                return querySnapshot.toObjects(Alumno::class.java)
            }
        } catch (e: Exception) {
            Log.e("LogicaAlumnos", "Error al obtener alumnos de Firestore", e)
            emptyList()
        }
    }

//    fun getNombreClase(clase: DocumentReference): String{
//        val user = auth.currentUser
//        val email = user?.email
//        val db = FirebaseFirestore.getInstance()
//        val coleccion = db.collection("alumnos")
//
//        var nombreClase = ""
//
//        clase.get()
//            .addOnSuccessListener { document ->
//                if (document != null && document.exists()) {
////                    // Accede a los datos del documento
////                    val datos = document.data
////                    // Procesa los datos según sea necesario
////                    nombreClase = datos?.get("nombre").toString()
//
//                    nombreClase = document.getString("nombre") ?: ""
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.e("Error clases", "Hubo un error obteniendo el nombre de la clase", exception)
//            }
//        Log.d("nombre Clase", nombreClase)
//        return nombreClase
//    }

//    fun getNombreClase(clase: DocumentReference, onComplete: (String) -> Unit) {
//        clase.get()
//            .addOnSuccessListener { document ->
//                if (document != null && document.exists()) {
//                    val nombreClase = document.getString("nombre") ?: ""
//                    onComplete(nombreClase)
//                } else {
//                    onComplete("")
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.e("Error clases", "Hubo un error obteniendo el nombre de la clase", exception)
//                onComplete("")
//            }
//    }

    suspend fun getNombreClase(clase: DocumentReference): String {
        return try {
            val document = clase.get().await()
            document.getString("nombre") ?: "Nombre no disponible"
        } catch (e: Exception) {
            Log.e("Error clases", "Hubo un error obteniendo el nombre de la clase", e)
            "Error al obtener nombre"
        }
    }
}