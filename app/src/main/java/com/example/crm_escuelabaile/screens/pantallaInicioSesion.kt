package com.example.crm_escuelabaile.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.crm_escuelabaile.R
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

    Box(modifier = Modifier.fillMaxSize()){
        Image(
            painter = painterResource(id = R.drawable.iniciosesion_1_background),
            contentDescription = "Imagen de fondo",
            contentScale = ContentScale.FillHeight,
            modifier = Modifier.fillMaxSize() // La imagen ocupará todo el espacio del `Box`
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "¡Hola!",
                fontSize = 64.sp,

                )
            Spacer(modifier = Modifier.height(35.dp))
            Text(
                text = "Iniciar sesion en la aplicación",
                modifier = Modifier,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(35.dp))

            TextField(
                value = email,
                //Pasamos el valor del email en el input a la funcion de la logica
                onValueChange = { logicaInicioSesion.onEmailChange(it) },
                label = { Text("EMAIL") },
                shape = RoundedCornerShape(30.dp),
                modifier = Modifier
                    .width(300.dp)
                    .clip(RoundedCornerShape(30.dp))
            )
            Spacer(modifier = Modifier.height(35.dp))

            TextField(
                value = password,
                onValueChange = { logicaInicioSesion.onPasswordChange(it) },
                label = { Text("PASSWORD") },
                shape = RoundedCornerShape(30.dp),
                modifier = Modifier
                    .width(300.dp)
                    .clip(RoundedCornerShape(30.dp))

            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "¿Contraseña olvidada?",
                modifier = Modifier,
            )

            Spacer(modifier = Modifier.height(36.dp))
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically
            ){

                    Text(
                        text = "Iniciar sesion",
                        modifier = Modifier
                            .clickable { logicaInicioSesion.iniciarSesion() },
                        fontSize = 30.sp

                    )
                Button(
                    onClick = { logicaInicioSesion.iniciarSesion() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent // Hace el fondo del botón transparente
                    ),
                    modifier = Modifier
                        .width(80.dp)
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    colorResource(id = R.color.purple_200), // Color inicial del gradiente
                                    colorResource(id = R.color.purple_500)    // Color final del gradiente
                                )
                            ),
                            shape = RoundedCornerShape(32.dp) // Redondea las esquinas del botón
                        )
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowForward,
                        contentDescription = "Arrow Forward",
                        modifier = Modifier.size(35.dp),

                    )
                }


            }


            Spacer(modifier = Modifier.height(50.dp))
            Row(modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "¿No tienes una cuenta? "
                )
                Text(
                    text = "Usa codigo",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { navController.navigate(route = "pantallaRegistro") }
                )
            }
            Spacer(modifier = Modifier.height(50.dp))
            Image(
                painter = painterResource(id = R.drawable.image_removebg_preview__3),
                contentDescription = null,
                modifier = Modifier.size(200.dp)
            )
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
    }

    //Despues de las consecuencias de cambiar los atributos del estadoInicioSesion lanzamos el LaunchedEfect
    LaunchedEffect(estadoInicioSesion) {
        //Si el estado del inicio de sesion es igual a exito (campos correctos),mandamos al usuario a pantallaDePrueba
        if (estadoInicioSesion == EstadoInicioSesion.EXITO) {
            navController.navigate("pantallaPrincipal")
        }
    }
}
