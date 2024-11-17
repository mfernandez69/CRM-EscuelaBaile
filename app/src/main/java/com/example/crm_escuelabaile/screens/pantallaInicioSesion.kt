package com.example.crm_escuelabaile.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.crm_escuelabaile.controllers.LogicaInicioSesion
import com.example.crm_escuelabaile.models.EstadoInicioSesion

@Composable
fun PantallaInicioSesion(navController: NavHostController, logicaInicioSesion: LogicaInicioSesion = viewModel()) {
    val email by logicaInicioSesion.email.collectAsState()
    val password by logicaInicioSesion.password.collectAsState()
    val estadoInicioSesion by logicaInicioSesion.estadoInicioSesion.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = { logicaInicioSesion.onEmailChange(it) },
            label = { Text("EMAIL") },
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { logicaInicioSesion.onPasswordChange(it) },
            label = { Text("PASSWORD") },
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                logicaInicioSesion.iniciarSesion()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Iniciar Sesión")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = when (estadoInicioSesion) {
                EstadoInicioSesion.INICIAL -> ""
                EstadoInicioSesion.CARGANDO -> "Iniciando sesión..."
                EstadoInicioSesion.EXITO -> "Inicio de sesión exitoso"
                EstadoInicioSesion.ERROR -> "Error al iniciar sesión"
            },
            color = when (estadoInicioSesion) {
                EstadoInicioSesion.EXITO -> MaterialTheme.colorScheme.primary
                EstadoInicioSesion.ERROR -> MaterialTheme.colorScheme.error
                else -> MaterialTheme.colorScheme.onSurface
            }
        )
    }

    LaunchedEffect(estadoInicioSesion) {
        if (estadoInicioSesion == EstadoInicioSesion.EXITO) {
            navController.navigate("pantallaDePrueba")
        }
    }
}
