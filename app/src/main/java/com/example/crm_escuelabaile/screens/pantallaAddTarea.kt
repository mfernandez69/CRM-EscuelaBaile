package com.example.crm_escuelabaile.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.navigation.NavHostController
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.crm_escuelabaile.controllers.LogicaInicioSesion
import com.example.crm_escuelabaile.controllers.LogicaMenu
import androidx.compose.material3.TimeInput
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import com.example.crm_escuelabaile.controllers.LogicaAddTarea
import com.google.firebase.Timestamp
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaAddTarea(
    navHostController: NavHostController,
    logicaAddTarea: LogicaAddTarea = viewModel()
){
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    titleContentColor = Color.Black,
                ),
                title = {
                    Text("Añadir Tarea")
                }
                ,
                navigationIcon = {
                    IconButton(onClick = { navHostController.navigate("PantallaAgenda") },
                        colors = IconButtonDefaults.iconButtonColors(contentColor = Color.Black)) {
                        Icon(
                            imageVector = Icons.Rounded.Close,
                            contentDescription = "Localized description"
                        )
                    }
                }
            )
        }
    ){ innerPadding ->
        FormularioTarea(innerPadding, logicaAddTarea, navHostController)
    }
}

@SuppressLint("StateFlowValueCalledInComposition", "WeekBasedYear")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioTarea(innerPadding: PaddingValues,logicaAddTarea: LogicaAddTarea, navHostController: NavHostController){
    val calendarState = rememberSheetState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        Spacer(modifier = Modifier.size(10.dp))

        // Nombre de la tarea
        val nombre by logicaAddTarea.nombre.collectAsState()
        OutlinedTextField(
            value = nombre,
            onValueChange = { logicaAddTarea.updateNombre(it) },
            label = { Text("Nombre de la tarea") }
        )

        Spacer(modifier = Modifier.size(10.dp))

        // Descripción
        val descripcion by logicaAddTarea.descripcion.collectAsState()
        OutlinedTextField(
            value = descripcion,
            onValueChange = { logicaAddTarea.updateDescripcion(it) },
            label = { Text("Descripción") }
        )
        Spacer(modifier = Modifier.size(10.dp))


        val currentLocalDate = LocalDateTime.now().toLocalDate()
        val diaHoy = Date.from(currentLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
        val datePickerState = rememberDatePickerState()
        datePickerState.displayMode = DisplayMode.Input

        LaunchedEffect(Unit) {

            logicaAddTarea.setDiaSeleccionado(diaHoy)
        }

        val diaSeleccionado by logicaAddTarea.diaSeleccionado.collectAsState()
        CalendarDialog(
            state = calendarState,
            config = CalendarConfig(
                monthSelection = true,
                yearSelection = true
            ),
            selection = CalendarSelection.Date {date ->
                val dia = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant())
                logicaAddTarea.setDiaSeleccionado(dia)
            }
        )

        val diaFormateado = diaSeleccionado?.let {dia ->
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            sdf.format(dia)
        }

        Button(
            onClick = {
                calendarState.show()
            }
        ) {
            Text(text = diaFormateado.toString())
        }


        LaunchedEffect(datePickerState.selectedDateMillis) {
            datePickerState.selectedDateMillis?.let { millis ->
                // val fecha = Timestamp(millis / 1000, 0)
                val dia = Date(millis)
                logicaAddTarea.setDiaSeleccionado(dia)
            }
        }

        Spacer(modifier = Modifier.size(10.dp))

        val currentTime = Calendar.getInstance()

        val timePickerState = rememberTimePickerState(
            initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
            initialMinute = currentTime.get(Calendar.MINUTE),
            is24Hour = true,
        )

        TimeInput(
            state = timePickerState,
        )

        LaunchedEffect(timePickerState.hour, timePickerState.minute) {
            val calendar = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                set(Calendar.MINUTE, timePickerState.minute)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }

            logicaAddTarea.setHoraSeleccionada(calendar)
        }

        Spacer(modifier = Modifier.size(10.dp))

        // Ubicación
        val ubicacion by logicaAddTarea.ubicacion.collectAsState()
        OutlinedTextField(
            value = ubicacion,
            onValueChange = { logicaAddTarea.updateUbicacion(it) },
            label = { Text("Ubicación") }
        )

        Button(
            onClick = {
                logicaAddTarea.addTarea()
                navHostController.navigate("PantallaAgenda")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Añadir Tarea")
        }
    }


}


