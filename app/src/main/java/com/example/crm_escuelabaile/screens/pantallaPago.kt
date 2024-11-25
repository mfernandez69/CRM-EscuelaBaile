package com.example.crm_escuelabaile.screens

import NotificacionViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.crm_escuelabaile.controllers.LogicaInicioSesion
import com.example.crm_escuelabaile.controllers.LogicaMenu
import com.example.crm_escuelabaile.controllers.LogicaPagos
import com.example.crm_escuelabaile.models.Alumno
import com.example.crm_escuelabaile.utils.MenuLateral
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaPago(
    navController: NavHostController,
    logicaMenu: LogicaMenu = viewModel(),
    logicaInicioSesion: LogicaInicioSesion = viewModel(),
    logicaPagos: LogicaPagos = viewModel()
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    MenuLateral(
        navController = navController,
        drawerState = drawerState,
        logicaMenu = logicaMenu,
        logicaInicioSesion = logicaInicioSesion
    ) { paddingValues ->
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text("Pagos") },
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
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                PagosTabLayout(logicaPagos)
            }
        }
    }
}

@Composable
fun PagosTabLayout(logicaPagos: LogicaPagos) {
    val pagerState = rememberPagerState(pageCount = { 2 })
    val cantidadNoPagos by logicaPagos.cantidadNoPagos.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    Column {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            contentColor = Color.White
        ) {
            Tab(
                selected = pagerState.currentPage == 0,
                onClick = { coroutineScope.launch { pagerState.animateScrollToPage(0) } }
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Sin pagar",
                        color = Color.Black
                    )
                    if (cantidadNoPagos > 0) {
                        Box(
                            modifier = Modifier
                                .padding(start = 4.dp)
                                .size(24.dp)
                                .background(Color.Red, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = cantidadNoPagos.toString(),
                                color = Color.White,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }

            Tab(
                selected = pagerState.currentPage == 1,
                onClick = { coroutineScope.launch { pagerState.animateScrollToPage(1) } }
            ) {
                Text(text = "Pagados", color = Color.Black)
            }
        }

        HorizontalPager(state = pagerState) { page ->
            when (page) {
                0 -> AlumnosSinPagar(logicaPagos)
                1 -> AlumnosQuePagaron(logicaPagos)
            }
        }
    }
}

@Composable
fun AlumnosSinPagar(logicaPagos: LogicaPagos) {
    val alumnosSinPagar by logicaPagos.AlumnosSinPagar.collectAsState()
    if (alumnosSinPagar.isEmpty()) {
        Text("No hay alumnos sin pagar", Modifier.padding(16.dp))
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(alumnosSinPagar) { alumno ->
                AlumnoItem(alumno, logicaPagos, esPagado = false)
            }
        }
    }
}

@Composable
fun AlumnosQuePagaron(logicaPagos: LogicaPagos) {
    val alumnosQuePagaron by logicaPagos.AlumnosQuePagaron.collectAsState()
    if (alumnosQuePagaron.isEmpty()) {
        Text("No hay alumnos que hayan pagado", Modifier.padding(16.dp))
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(alumnosQuePagaron) { alumno ->
                AlumnoItem(alumno, logicaPagos, esPagado = true)
            }
        }
    }
}

@Composable
fun AlumnoItem(alumno: Alumno, logicaPagos: LogicaPagos, esPagado: Boolean) {
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
            Text(text = "Nombre: ${alumno.nombre}", fontWeight = FontWeight.Bold)
            Text(text = "Email: ${alumno.email}")
            Text(text = "DNI: ${alumno.dni}")
            alumno.fechaDeNacimiento?.let { fecha ->
                Text(
                    text = "Fecha de Nacimiento: ${
                        SimpleDateFormat(
                            "dd/MM/yyyy",
                            Locale.getDefault()
                        ).format(fecha.toDate())
                    }"
                )
            }
        }
    }
}