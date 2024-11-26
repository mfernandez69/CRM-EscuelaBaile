package com.example.crm_escuelabaile.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.crm_escuelabaile.R
import com.example.crm_escuelabaile.controllers.LogicaRegistro
import com.example.crm_escuelabaile.models.EstadoInicioSesion
import com.example.crm_escuelabaile.models.EstadoRegistro

@OptIn(ExperimentalMaterial3Api::class)
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
    Box(modifier = Modifier.fillMaxSize()) {
        //IMAGEN DE FONDO
        Image(
            painter = painterResource(id = R.drawable.crearcuenta), // Cambia a tu recurso de imagen
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillHeight // Escala la imagen para cubrir toda la pantalla
        )
        IconButton(
            onClick = { navController.navigate("pantallaInicioSesion") },
            modifier = Modifier
                .padding(16.dp)
                .offset(y = 16.dp) // Desplaza hacia abajo 16.dp
                .size(40.dp)
                .align(Alignment.TopStart)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Volver",
                tint = Color(0xFFf8526a) // Color #f8526a
            )
        }

        // CONTENIDO PRINCIPAL
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
                shape = RoundedCornerShape(30.dp),
                modifier = Modifier
                    .width(300.dp)
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(30.dp),
                        clip = false
                    )
                    .clip(RoundedCornerShape(30.dp))
                    .background(Color.White),

                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Person, // Cambia este ícono según lo necesites
                        contentDescription = "Icono de validación",
                        tint = Color.Black // Color del ícono
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { logicaRegistro.onPasswordChange(it) },
                label = { Text("PASSWORD") },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .width(300.dp)
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(30.dp),
                        clip = false
                    )
                    .clip(RoundedCornerShape(30.dp))
                    .background(Color.White),

                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Lock, // Cambia este ícono según lo necesites
                        contentDescription = "Icono de validación",
                        tint = Color.Black // Color del ícono
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = nombreCompleto,
                onValueChange = { logicaRegistro.onNameChange(it) },
                label = { Text("NOMBRE COMPLETO") },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .width(300.dp)
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(30.dp),
                        clip = false
                    )
                    .clip(RoundedCornerShape(30.dp))
                    .background(Color.White),

                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.AccountCircle, // Cambia este ícono según lo necesites
                        contentDescription = "Icono de validación",
                        tint = Color.Black // Color del ícono
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = dni,
                onValueChange = { logicaRegistro.onDniChange(it) },
                label = { Text("DNI") },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .width(300.dp)
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(30.dp),
                        clip = false
                    )
                    .clip(RoundedCornerShape(30.dp))
                    .background(Color.White),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.AccountBox, // Cambia este ícono según lo necesites
                        contentDescription = "Icono de validación",
                        tint = Color.Black // Color del ícono
                    )
                },
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = telefono,
                onValueChange = { logicaRegistro.onTelefonoChange(it) },
                label = { Text("TELEFONO") },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .width(300.dp)
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(30.dp),
                        clip = false
                    )
                    .clip(RoundedCornerShape(30.dp))
                    .background(Color.White),

                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Call, // Cambia este ícono según lo necesites
                        contentDescription = "Icono de validación",
                        tint = Color.Black // Color del ícono
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    logicaRegistro.registrarUsuario()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent // Hace el fondo del botón transparente
                ),
                modifier = Modifier
                    .width(300.dp)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                colorResource(id = R.color.purple_200), // Color inicial del gradiente
                                colorResource(id = R.color.purple_500)    // Color final del gradiente
                            )
                        ),
                        shape = RoundedCornerShape(20.dp) // Redondea las esquinas del botón
                    )

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