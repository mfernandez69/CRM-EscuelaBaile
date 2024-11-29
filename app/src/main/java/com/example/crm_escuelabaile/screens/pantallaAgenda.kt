package com.example.crm_escuelabaile.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.crm_escuelabaile.controllers.LogicaAgenda
import com.example.crm_escuelabaile.controllers.LogicaInicioSesion
import com.example.crm_escuelabaile.controllers.LogicaMenu
import com.example.crm_escuelabaile.models.Notificacion
import com.example.crm_escuelabaile.models.Tarea
import com.example.crm_escuelabaile.utils.MenuLateral
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import com.google.firebase.Timestamp
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaAgenda(
    navHostController: NavHostController,
    logicaMenu: LogicaMenu = viewModel(),
    logicaInicioSesion: LogicaInicioSesion = viewModel()
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    MenuLateral(
        navController = navHostController,
        drawerState = drawerState,
        logicaMenu = logicaMenu,
        logicaInicioSesion=logicaInicioSesion
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
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { navHostController.navigate("PantallaAddTarea") },
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Agregar evento"
                    )
                }
            },
            floatingActionButtonPosition = FabPosition.End
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
    // Obtener la fecha actual en milisegundos desde la época
    val currentInstant = Clock.System.now()
    val currentDateTime = currentInstant.toLocalDateTime(TimeZone.currentSystemDefault())
    val millisHoy = currentDateTime.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()

    val datePickerState = rememberDatePickerState()
    LaunchedEffect(Unit) {
        val fechaHoyTimestamp = Timestamp(millisHoy / 1000, 0)
        logicaAgenda.setFechaSeleccionada(fechaHoyTimestamp)
    }
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
    val horaFormateada = tarea.fecha?.let { timestamp ->
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        sdf.format(timestamp.toDate())
    } ?: "Sin hora"

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        // Sombra en el lado izquierdo
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(8.dp) // Ancho de la sombra
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color(0x80000000), // Sombra negra semitransparente
                            Color.Transparent // Gradiente hacia transparente
                        )
                    )
                )
                .align(Alignment.CenterStart) // Alinea la sombra al lado izquierdo
        )

        // Tarjeta de contenido con fondo blanco, borde y elevación
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp) // Deja espacio para la sombra
                .border(2.dp, Color(0xFF00FFFF), MaterialTheme.shapes.medium), // Borde cyan
            shape = MaterialTheme.shapes.medium,
            elevation = CardDefaults.elevatedCardElevation(8.dp) // Elevación
        ) {
            // Aquí añadimos el fondo blanco directamente al Card
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White) // Fondo blanco dentro del Card
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = horaFormateada,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Text(
                        text = tarea.descripcion ?: "Sin descripción",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 8.dp)
                    )

                    Row(
                        modifier = Modifier.padding(top = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Ubicación",
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = tarea.ubicacion ?: "Sin ubicación",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }
        }
    }
}





