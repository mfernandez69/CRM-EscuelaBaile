package com.example.crm_escuelabaile.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        OutlinedTextField(
            value = email,
            //Pasamos el valor del email en el input a la funcion de la logica
            onValueChange = { logicaRegistro.onEmailChange(it) },
            label = { Text("EMAIL") },
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
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
                //Aqui definimos el texto de cada estado del inicio de sesion
                //El texto que aparezca depende de la logica del inicio de sesion
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
    }
    LaunchedEffect(estadoRegistro) {
        //Si el estado del inicio de sesion es igual a exito (campos correctos),mandamos al usuario a pantallaDePrueba
        if (estadoRegistro == EstadoRegistro.EXITO) {
            navController.navigate("pantallaPrincipal")
        }
    }
}