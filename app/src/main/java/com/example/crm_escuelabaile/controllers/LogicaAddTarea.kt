package com.example.crm_escuelabaile.controllers

import android.util.Log
import android.widget.TimePicker
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.time.ZoneId
import java.util.Calendar
import java.util.Date

class LogicaAddTarea : ViewModel(){
    val auth: FirebaseAuth = Firebase.auth

    private val _nombre = MutableStateFlow("")
    val nombre: StateFlow<String> get() = _nombre.asStateFlow()

    private val _descripcion = MutableStateFlow("")
    val descripcion: StateFlow<String> get() = _descripcion.asStateFlow()

    private val _fecha = MutableStateFlow<Timestamp?>(null)
    val fecha: StateFlow<Timestamp?> get() = _fecha.asStateFlow()

    private val _diaSeleccionado = MutableStateFlow<Date?>(null)
    val diaSeleccionado: StateFlow<Date?> get() = _diaSeleccionado.asStateFlow()

    private val _horaSeleccionada = MutableStateFlow<Calendar?>(null)
    val horaSeleccionada: StateFlow<Calendar?> get() = _horaSeleccionada.asStateFlow()

    private val _ubicacion = MutableStateFlow("")
    val ubicacion: StateFlow<String> get() = _ubicacion.asStateFlow()


    fun updateNombre(value: String) {
        _nombre.value = value
    }

    fun updateDescripcion(value: String) {
        _descripcion.value = value
    }

    fun updateFecha(){
        _fecha.value = combinarFechaHora()
    }

    fun setDiaSeleccionado(dia: Date) {
        _diaSeleccionado.value = dia
    }

    fun setHoraSeleccionada(hora: Calendar) {
        _horaSeleccionada.value = hora
    }

    fun updateUbicacion(value: String) {
        _ubicacion.value = value
    }

    private fun combinarFechaHora(): Timestamp {
        val fechaSeleccionada = Calendar.getInstance()
        fechaSeleccionada.time = diaSeleccionado.value!!
        _horaSeleccionada.value?.let {
            fechaSeleccionada.set(Calendar.HOUR_OF_DAY, it.get(Calendar.HOUR_OF_DAY))
            fechaSeleccionada.set(Calendar.MINUTE, it.get(Calendar.MINUTE))
            fechaSeleccionada.set(Calendar.SECOND, it.get(Calendar.SECOND))
            fechaSeleccionada.set(Calendar.MILLISECOND, it.get(Calendar.MILLISECOND))
        }

        val fechaSeleccionadaTimestamp = Timestamp(fechaSeleccionada.time)

        return fechaSeleccionadaTimestamp
    }

    fun addTarea(){
        val user = auth.currentUser
        val email = user?.email
        val db = FirebaseFirestore.getInstance()
        val coleccion = db.collection("tarea")

        updateFecha()

        viewModelScope.launch {
            if (_nombre.value.isNotBlank()) {
                try {

                    val datosTarea = hashMapOf(
                        "nombre" to _nombre.value,
                        "descripcion" to _descripcion.value,
                        "fecha" to _fecha.value,
                        "ubicacion" to _ubicacion.value,
                        "email" to email
                    )
                    coleccion.document().set(datosTarea).await()


                } catch (e: Exception) {
                    Log.e("LogicaAddTarea", "Error al introducir tareas en Firestore", e)
                }
            } else {
                Log.e("LogicaAddTarea", "Datos insuficientes")
            }
        }
    }


}