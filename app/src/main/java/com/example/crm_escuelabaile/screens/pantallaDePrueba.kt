package com.example.crm_escuelabaile.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.crm_escuelabaile.controllers.LogicaPantallaPrueba

@Composable
fun PantallaDePrueba(navController: NavHostController,logicaPantallaPrueba: LogicaPantallaPrueba= viewModel()) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            navController.navigate("pantallaSecundaria")
        }) {
            Text(text = "Ir a Pantalla Secundaria")
        }
        Button(onClick = {
            logicaPantallaPrueba.aumentarContador()
        }) {
            Text(text = "Aumentar contador")
        }
        Text(
            text = "Contador: ${logicaPantallaPrueba.contador}"
        )
    }
}
