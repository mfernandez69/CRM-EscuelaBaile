package com.example.crm_escuelabaile.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
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
import androidx.compose.runtime.collectAsState
import com.example.crm_escuelabaile.controllers.LogicaAddTarea
import java.util.Calendar

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
        FormularioTarea(innerPadding,logicaAddTarea)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioTarea(innerPadding: PaddingValues,logicaAddTarea: LogicaAddTarea){

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
        val datePickerState = rememberDatePickerState()
        datePickerState.displayMode = DisplayMode.Input


        DatePicker(
            state = datePickerState,
            showModeToggle = false,
            title = null,
            headline = null
        )
        Spacer(modifier = Modifier.size(10.dp))

        val currentTime = Calendar.getInstance()

        val timePickerState = rememberTimePickerState(
            initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
            initialMinute = currentTime.get(Calendar.MINUTE),
            is24Hour = true,
        )

        val hora by remember { mutableStateOf("") }
        TimeInput(
            state = timePickerState,
        )


        Spacer(modifier = Modifier.size(10.dp))

        // Email del recipiente
        val email by logicaAddTarea.email.collectAsState()
        OutlinedTextField(
            value = email,
            onValueChange = { logicaAddTarea.updateEmail(it) },
            label = { Text("Email del recipiente") }
        )

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
                // Aquí puedes llamar a una función en LogicaAddTarea para procesar la tarea
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Añadir Tarea")
        }
    }
}

