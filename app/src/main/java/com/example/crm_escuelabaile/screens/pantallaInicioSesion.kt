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
fun PantallaInicioSesion(
    navController: NavHostController,
    logicaInicioSesion: LogicaInicioSesion = viewModel()
) {
    //Se utilizan collectAsState() para observar los cambios en el ViewModel.
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
            //Pasamos el valor del email en el input a la funcion de la logica
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
        Button(
            onClick = {
                navController.navigate("pantallaRegistro")
            }
        ) {
            Text(
                text = "Registrarse"
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = when (estadoInicioSesion) {
                //Aqui definimos el texto de cada estado del inicio de sesion
                //El texto que aparezca depende de la logica del inicio de sesion
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
    //Despues de las consecuencias de cambiar los atributos del estadoInicioSesion lanzamos el LaunchedEfect
    LaunchedEffect(estadoInicioSesion) {
        //Si el estado del inicio de sesion es igual a exito (campos correctos),mandamos al usuario a pantallaDePrueba
        if (estadoInicioSesion == EstadoInicioSesion.EXITO) {
            navController.navigate("pantallaPrincipal")
        }
    }
}
