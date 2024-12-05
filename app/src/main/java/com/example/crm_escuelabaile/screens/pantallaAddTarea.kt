package com.example.crm_escuelabaile.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.navigation.NavHostController
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import com.example.crm_escuelabaile.R
import com.example.crm_escuelabaile.controllers.LogicaAddTarea
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockConfig
import com.maxkeppeler.sheets.clock.models.ClockSelection
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.util.Calendar
import java.util.Date
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
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
                },
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

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun FormularioTarea(innerPadding: PaddingValues,logicaAddTarea: LogicaAddTarea, navHostController: NavHostController){
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
            label = { Text("Nombre de la tarea",
                fontSize = 20.sp) },

            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.Info, // Cambia este ícono según lo necesites
                    contentDescription = "Icono de validación",
                    tint = Color.Black // Color del ícono
                )
            },
            shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            modifier = Modifier
                .width(300.dp)
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(10.dp),
                    clip = false
                )
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White)
        )

        Spacer(modifier = Modifier.size(30.dp))

        // Descripción
        val descripcion by logicaAddTarea.descripcion.collectAsState()
        OutlinedTextField(
            value = descripcion,
            onValueChange = { logicaAddTarea.updateDescripcion(it) },
            label = { Text("Descripción",
                fontSize = 20.sp) },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.Edit, // Cambia este ícono según lo necesites
                    contentDescription = "Icono de validación",
                    tint = Color.Black // Color del ícono
                )
            },
            shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            modifier = Modifier
                .width(300.dp)
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(10.dp),
                    clip = false
                )
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White)
        )
        Spacer(modifier = Modifier.size(30.dp))

        // Ubicación
        val ubicacion by logicaAddTarea.ubicacion.collectAsState()

        OutlinedTextField(
            value = ubicacion,
            onValueChange = { logicaAddTarea.updateUbicacion(it) },
            label = { Text("Ubicación",
                fontSize = 20.sp) },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.LocationOn, // Cambia este ícono según lo necesites
                    contentDescription = "Icono de validación",
                    tint = Color.Black // Color del ícono
                )
            },
            shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            modifier = Modifier
                .width(300.dp)
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(10.dp),
                    clip = false
                )
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White)
        )

        Spacer(modifier = Modifier.size(30.dp))

        // FECHA
        // Selecciona la fecha actual para ponerla como valor inicial
        val currentLocalDate = LocalDateTime.now().toLocalDate()
        val diaHoy = Date.from(currentLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
        // Pone la fecha actual como valor inicial
        LaunchedEffect(Unit) { logicaAddTarea.setDiaSeleccionado(diaHoy) }
        // Imprime el Selector de fecha
        CalendarioDia(logicaAddTarea)

        Spacer(modifier = Modifier.size(20.dp))

        // HORA
        // Selecciona la fecha actual para ponerla como valor inicial
        val currentTime = Calendar.getInstance()
        // Pone la hora actual como valor inicial
        LaunchedEffect(Unit) { logicaAddTarea.setHoraSeleccionada(currentTime) }
        // Imprime el Selector de hora
        CalendarioHora(logicaAddTarea)


        Spacer(modifier = Modifier.size(50.dp))


            Button(
                onClick = {
                    logicaAddTarea.addTarea()
                    navHostController.navigate("PantallaAgenda")
                },
                modifier = Modifier
                    .width(300.dp)
                    .background(Color.White), // Fondo blanco
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF375F),
                    contentColor = Color.White          // Texto negro
                ),
                elevation = ButtonDefaults.elevatedButtonElevation( // Elevación interna del botón
                    defaultElevation = 0.dp, // Sin elevación interna
                    pressedElevation = 0.dp,
                    disabledElevation = 0.dp
                )
            ) {
                Text(text = "Añadir Tarea", fontSize = 23.sp)
            }
        Spacer(modifier = Modifier.size(50.dp))
        Image(
            modifier = Modifier.size(200.dp),
            painter = painterResource(id = R.drawable.logo_shake_it),
            contentDescription = null
        )
        }

    }


@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarioDia(logicaAddTarea: LogicaAddTarea){
    val calendarState = rememberSheetState()
    val diaSeleccionado by logicaAddTarea.diaSeleccionado.collectAsState()
    val dateList: List<LocalDate> = LocalDate
        .now()
        .minusYears(100)
        .datesUntil(LocalDate.now())
        .toList()
    CalendarDialog(
        state = calendarState,
        config = CalendarConfig(
            monthSelection = true,
            yearSelection = true,
            disabledDates = dateList
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
        modifier = Modifier.width(300.dp)
            .border(
                width = 2.dp,                     // Ancho del borde
                color = Color.Black,              // Color del borde
                shape = RoundedCornerShape(10.dp)
                ),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFFFFFFF), // Cambia el color de fondo
            contentColor = Color.Black          // Cambia el color del texto
        ),
        onClick = {
            calendarState.show()
        }
    ) {
        Text(text = diaFormateado.toString(),
            fontSize = 20.sp)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarioHora(logicaAddTarea: LogicaAddTarea, modifier: Modifier = Modifier){
    val clockState = rememberSheetState()
    val horaSeleccionada by logicaAddTarea.horaSeleccionada.collectAsState()
    ClockDialog(
        state = clockState,
        config = ClockConfig(
            is24HourFormat = true,
            defaultTime = LocalTime.now()
        ),
        selection = ClockSelection.HoursMinutes { hours, minutes ->
            val hora = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, hours)
                set(Calendar.MINUTE, minutes)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }
            logicaAddTarea.setHoraSeleccionada(hora)
        }
    )

    val horaFormateada = horaSeleccionada?.let {hora ->
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        sdf.format(hora.time)
    }

    Button(
        modifier = Modifier.width(300.dp)
        .border(
            width = 2.dp,                     // Ancho del borde
    color = Color.Black,              // Color del borde
    shape = RoundedCornerShape(10.dp)
    ),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFFFFFFF), // Cambia el color de fondo
            contentColor = Color.Black          // Cambia el color del texto
        ),
        onClick = {
            clockState.show()
        }
    ) {
        Text(text = horaFormateada.toString(),
            fontSize = 20.sp)
    }
}

