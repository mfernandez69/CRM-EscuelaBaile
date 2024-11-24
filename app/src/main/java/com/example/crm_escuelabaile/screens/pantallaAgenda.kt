package com.example.crm_escuelabaile.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import java.time.Instant
import java.time.ZoneId
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.crm_escuelabaile.controllers.LogicaAgenda
import com.example.crm_escuelabaile.controllers.LogicaMenu
import com.example.crm_escuelabaile.models.Notificacion
import com.example.crm_escuelabaile.models.Tarea
import com.example.crm_escuelabaile.utils.MenuLateral
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import com.google.firebase.Timestamp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaAgenda(
    navHostController: NavHostController,
    logicaMenu: LogicaMenu = viewModel()
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    MenuLateral(
        navController = navHostController,
        drawerState = drawerState,
        logicaMenu = logicaMenu
    ) { paddingValues ->
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text("Agenda") },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.open()
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = "Abrir el menu"
                            )
                        }
                    }
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 30.dp)
                    .padding(horizontal = innerPadding.calculateStartPadding(LayoutDirection.Ltr))
            ) {
                SeccionCalendario(navHostController)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeccionCalendario(navHostController: NavHostController, logicaAgenda: LogicaAgenda = viewModel()) {
    val datePickerState = rememberDatePickerState()

    LaunchedEffect(datePickerState.selectedDateMillis) {
        datePickerState.selectedDateMillis?.let { millis ->
            val fecha = Timestamp(millis / 1000, 0)
            logicaAgenda.setFechaSeleccionada(fecha)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(450.dp)
        ) {
            DatePicker(
                state = datePickerState,
                showModeToggle = false,
                title = {},
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            ListaTareas(logicaAgenda)
        }
    }
}
@Composable
fun ListaTareas(logicaAgenda: LogicaAgenda = viewModel()) {
    val tareas by logicaAgenda.tareasDelDia.collectAsState()

    if (tareas.isEmpty()) {
        Text("No hay tareas para hoy", Modifier.padding(16.dp))
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            items(tareas) { tarea ->
                TareaItem(tarea)
            }
        }
    }
}
@Composable
fun TareaItem(tarea: Tarea) {
    //Creamos una card para cada notificacion con la info del objeto
    val fechaFormateada = tarea.fecha?.let { timestamp ->
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        sdf.format(timestamp.toDate())
    } ?: "Sin fecha"
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = tarea.nombre ?: "Sin título", fontWeight = FontWeight.Bold)
            Text(text = tarea.descripción ?: "Sin descripción")
            Text(text = tarea.ubicacion ?: "Sin ubicación")
            Text(text = "Fecha: $fechaFormateada")
        }
    }
}