package com.example.crm_escuelabaile.screens

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.crm_escuelabaile.controllers.LogicaAlumnos
import com.example.crm_escuelabaile.controllers.LogicaInicioSesion
import com.example.crm_escuelabaile.controllers.LogicaMenu
import com.example.crm_escuelabaile.models.Alumno
import com.example.crm_escuelabaile.utils.MenuLateral
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaAlumnos(
    navHostController: NavHostController,
    logicaMenu: LogicaMenu = viewModel(),
    logicaInicioSesion: LogicaInicioSesion = viewModel()
){
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    MenuLateral(
        navController = navHostController,
        drawerState = drawerState,
        logicaMenu = logicaMenu,
        logicaInicioSesion = logicaInicioSesion
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text("Alumnos") },
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
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                BusquedaAlumno()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BusquedaAlumno(logicaAlumnos: LogicaAlumnos = viewModel()){

    val alumnosFiltrados by logicaAlumnos.alumnosFiltrados.collectAsState()
    val nombre by logicaAlumnos.nombreAlumno.collectAsState()
    var active by remember { mutableStateOf(false) }
    val ctx = LocalContext.current

    logicaAlumnos.cargarAlumnos()

    SearchBar(
        query = nombre,  // Equivalente a value
        onQueryChange = { logicaAlumnos.updateNombreAlumno(it) },  // Equivalente a onValueChange
        onSearch = {
            Toast.makeText(ctx, logicaAlumnos.nombreAlumno.value, Toast.LENGTH_SHORT).show()
            active = false
            logicaAlumnos.cargarAlumnos()
        },    // Cuando se lanza la acción de busqueda
        active = active,  // Indica si la barra de busqueda esta siendo usada o no
        onActiveChange = { active = it },  // Cuando el valor de active se esta cambiando
        placeholder = { Text(text = "Buscar alumnos") },

        leadingIcon = {
            if (active){
                IconButton(onClick = { active = false } )
                {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "cerrar busqueda",
                    )
                }
            }
        },
        trailingIcon = {
            if (active){
                IconButton(
                    onClick = {
                        active = false
                        logicaAlumnos.updateNombreAlumno("")
                    }
                )
                {
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = "cancelar busqueda",
                    )
                }
            }
        },
        modifier = Modifier
            .fillMaxWidth(),

    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (nombre.isNotEmpty()){

                items(alumnosFiltrados) { alumno ->
                    AlumnoBusquedaItem(logicaAlumnos = logicaAlumnos, alumno = alumno)
                }
            }
        }
    }


    if (nombre.isNotEmpty()){
        ResultadosBusqueda(logicaAlumnos = logicaAlumnos)
    }

}

@Composable
private fun ResultadosBusqueda(logicaAlumnos: LogicaAlumnos = viewModel()){
    val alumnos by logicaAlumnos.alumnos.collectAsState()

    if (alumnos.isEmpty()) {
        Text("No hay alumnos con ese nombre", Modifier.padding(16.dp))
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(alumnos) { alumno ->
                AlumnoBusquedaItem(alumno = alumno, logicaAlumnos = logicaAlumnos)
            }
        }
    }
}

@Composable
private fun AlumnoBusquedaItem(alumno: Alumno, logicaAlumnos: LogicaAlumnos) {
    var mostrarPopup by remember { mutableStateOf(false) }
    val fechaNacimientoFormateada = alumno.fechaDeNacimiento?.let { timestamp ->
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        sdf.format(timestamp.toDate())
    } ?: "Sin fecha"
    Card(
        modifier = Modifier
            .width(300.dp)
            .clickable {
                mostrarPopup = true
                logicaAlumnos.setAlumnoMostrando(alumno)
            }
            .border(
                width = 2.dp,
                color = borderColorCard(),
                shape = RoundedCornerShape(10.dp)
            ),

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(26.dp)
        ) {
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically
            ){

                Text(text = alumno.nombre, fontSize = 20.sp,
                    fontWeight = FontWeight.Bold)

            }
            Spacer(modifier = Modifier.height(15.dp))
           Row(
               modifier = Modifier,
               verticalAlignment = Alignment.CenterVertically
           ) {

               Text(text = alumno.email
                   , fontSize = 20.sp)


           }
            Spacer(modifier = Modifier.height(15.dp))

        }
    }
    Spacer(modifier = Modifier.height(15.dp))
    if (mostrarPopup) {
        PopupAlumno(
            onDismissRequest = { mostrarPopup = false },
            logicaAlumnos = logicaAlumnos,
            alumno = alumno,
            fechaNacimientoFormateada = fechaNacimientoFormateada
        )
    }
}

//@Composable
//private fun mostrarAlumnoEspecifico(navHostController: NavHostController, alumno: Alumno, logicaAlumnos: LogicaAlumnos){
//    var ocultarPopup by remember { mutableStateOf(false) }
//    val fechaNacimientoFormateada = alumno.fechaDeNacimiento?.let { timestamp ->
//        val sdf = SimpleDateFormat("dd 'de' MMMM 'de' yyyy", Locale("es", "ES"))
//        sdf.format(timestamp.toDate())
//    } ?: "Sin fecha"
//
//    PopupAlumno(alumno = alumno, fechaNacimientoFormateada = fechaNacimientoFormateada, onDismissRequest = {})
//
////    if (ocultarPopup){
////        AlumnoBusquedaItem(navHostController = navHostController, alumno = alumno, logicaAlumnos = logicaAlumnos)
////    }
//}

@Composable
fun PopupAlumno(
    onDismissRequest: () -> Unit,
    logicaAlumnos: LogicaAlumnos,
    alumno: Alumno,
    fechaNacimientoFormateada: String
) {
    Dialog(onDismissRequest = onDismissRequest) {
        // Draw a rectangle shape with rounded corners inside the dialog
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(375.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                verticalArrangement = Arrangement.Center,
            ) {
                Text(text = "Nombre: ${alumno.nombre}", fontWeight = FontWeight.Bold)
                Text(text = "Email: ${alumno.email}")
                Text(text = "DNI: ${alumno.dni}")
                Text(text = "Fecha de Nacimiento: $fechaNacimientoFormateada")
                if (alumno.estadoPago){
                    Text(text = "Clases pagadas")
                }
                else{
                    Text(text = "Pagos no realizados")
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    item {
                        Text(
                            text = "Clases: "
                        )
                    }
                    items(alumno.clases) { clase ->
                        val nombreClase by produceState(initialValue = "Cargando...") {
                            value = logicaAlumnos.getNombreClase(clase)
                        }
                        Text(text = "- $nombreClase")
                    }
                }

                TextButton(
                    onClick = onDismissRequest,
                    modifier = Modifier.padding(8.dp),
                ) {
                    Text("Cerrar")
                }
            }
        }
    }
}

