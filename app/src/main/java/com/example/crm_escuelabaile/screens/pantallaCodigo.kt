package com.example.crm_escuelabaile.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.crm_escuelabaile.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaCodigo(navController: NavHostController){
Box(modifier = Modifier.fillMaxSize()){
    Image(
        painter = painterResource(R.drawable.iniciosesion_1_background),
        contentDescription = "Imagen de fondo",
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize() // La imagen ocupará todo el espacio del `Box`
    )
    IconButton(
        onClick = { navController.navigate("pantallaRegistro") },
        modifier = Modifier
            .padding(16.dp)
            .offset(y = 16.dp)
            .size(40.dp)
            .align(Alignment.TopStart)
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Volver",
            tint = Color.Black // Color #f8526a
        )
    }
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){
        Text(
            text = "Introduce tu codigo",
            fontSize = 30.sp
        )
        Spacer(modifier = Modifier.height(40.dp))
        TextField(
            value = "",
            onValueChange = {  },
            label = { Text("Codigo") },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.Lock, // Cambia este ícono según lo necesites
                    contentDescription = "Icono de validación",
                    tint = Color.Black // Color del ícono
                )
            },
            shape = RoundedCornerShape(30.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),

            modifier = Modifier
                .width(300.dp)
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(30.dp),
                    clip = false
                )
                .clip(RoundedCornerShape(30.dp))
                .background(Color.White)

        )
        Spacer(modifier = Modifier.height(40.dp))
        TextField(
            value = "",
            onValueChange = {  },
            label = { Text("DNI") },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.AccountBox, // Cambia este ícono según lo necesites
                    contentDescription = "Icono de validación",
                    tint = Color.Black // Color del ícono
                )
            },
            shape = RoundedCornerShape(30.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),

            modifier = Modifier
                .width(300.dp)
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(30.dp),
                    clip = false
                )
                .clip(RoundedCornerShape(30.dp))
                .background(Color.White)

        )

        Spacer(modifier = Modifier.height(40.dp))

        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically
        ){

            Text(
                text = "Siguiente",
                modifier = Modifier
                    .clickable {navController.navigate(route = "pantallaPrincipal")},
                fontSize = 30.sp

            )
            Button(
                onClick = {navController.navigate(route = "pantallaPrincipal") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent // Hace el fondo del botón transparente
                ),
                modifier = Modifier
                    .width(70.dp)
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
                Icon(
                    imageVector = Icons.Filled.ArrowForward,
                    contentDescription = "Arrow Forward",
                    modifier = Modifier.size(20.dp),

                    )
            }


        }


    }
}


}