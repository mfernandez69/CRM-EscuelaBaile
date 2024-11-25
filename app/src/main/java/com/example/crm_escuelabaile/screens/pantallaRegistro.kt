package com.example.crm_escuelabaile.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.crm_escuelabaile.R
import com.example.crm_escuelabaile.controllers.LogicaRegistro
import com.example.crm_escuelabaile.models.EstadoInicioSesion
import com.example.crm_escuelabaile.models.EstadoRegistro

@Composable
fun PantallaRegistro(
    navController: NavHostController,
    logicaRegistro: LogicaRegistro = viewModel()
) {
    val email by logicaRegistro.email.collectAsState()
    val password by logicaRegistro.password.collectAsState()
    val nombreCompleto by logicaRegistro.nombreCompleto.collectAsState()
    val dni by logicaRegistro.dni.collectAsState()
    val telefono by logicaRegistro.telefono.collectAsState()

    val estadoRegistro by logicaRegistro.estadoRegistro.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Imagen de fondo
        androidx.compose.foundation.Image(
            painter = painterResource(id = R.drawable.crearcuenta), // Cambia a tu recurso de imagen
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillHeight // Escala la imagen para cubrir toda la pantalla
        )

        // Contenido principal
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),

            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = email,
                onValueChange = { logicaRegistro.onEmailChange(it) },
                label = { Text("EMAIL") },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
                    .width(70.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { logicaRegistro.onPasswordChange(it) },
                label = { Text("PASSWORD") },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = nombreCompleto,
                onValueChange = { logicaRegistro.onNameChange(it) },
                label = { Text("NOMBRE COMPLETO") },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = dni,
                onValueChange = { logicaRegistro.onDniChange(it) },
                label = { Text("DNI") },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = telefono,
                onValueChange = { logicaRegistro.onTelefonoChange(it) },
                label = { Text("TELEFONO") },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    logicaRegistro.registrarUsuario()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Registrarme")
            }
            Text(
                text = when (estadoRegistro) {
                    EstadoRegistro.INICIAL -> ""
                    EstadoRegistro.CARGANDO -> "Registrando usuario..."
                    EstadoRegistro.EXITO -> "Registro exitoso"
                    EstadoRegistro.ERROR -> "Error al registrarse"
                },
                color = when (estadoRegistro) {
                    EstadoRegistro.EXITO -> MaterialTheme.colorScheme.primary
                    EstadoRegistro.ERROR -> MaterialTheme.colorScheme.error
                    else -> MaterialTheme.colorScheme.onSurface
                }
            )
            Spacer(modifier = Modifier.height(32.dp))
            Image(
                painter = painterResource(id = R.drawable.image_removebg_preview__3),
                contentDescription = "Logo de la aplicación",
                modifier = Modifier
                    .size(150.dp) // Ajusta el tamaño según lo necesites
            )

        }
    }

    LaunchedEffect(estadoRegistro) {
        if (estadoRegistro == EstadoRegistro.EXITO) {
            navController.navigate("pantallaPrincipal")
        }
    }
}