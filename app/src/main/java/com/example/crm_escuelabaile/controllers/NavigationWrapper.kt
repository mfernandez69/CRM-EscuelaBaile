package com.example.crm_escuelabaile.controllers

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.crm_escuelabaile.screens.*

@Composable
fun NavigationWrapper(navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = "pantallaInicioSesion") {
        composable("pantallaInicioSesion") {
            PantallaInicioSesion(navHostController)
        }
        composable("pantallaPrincipal") {
            PantallaPrincipal(navHostController)
        }
        composable("pantallaRegistro") {
            PantallaRegistro(navHostController)
        }
        composable("pantallaAgenda") {
            PantallaAgenda(navHostController)
        }
        composable("pantallaAddTarea") {
            PantallaAddTarea(navHostController)
        }
        composable("pantallaPago") {
            PantallaPago(navHostController)
        }
    }
}
