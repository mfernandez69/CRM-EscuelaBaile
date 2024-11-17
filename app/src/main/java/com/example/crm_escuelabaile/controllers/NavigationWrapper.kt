package com.example.crm_escuelabaile.controllers

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.crm_escuelabaile.screens.*

@Composable
fun NavigationWrapper(navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = "pantallaInicioSesion") {
        composable("pantallaDePrueba") {
            PantallaDePrueba(navHostController)
        }
        composable("pantallaSecundaria") {
            PantallaSecundaria(navHostController)
        }
        composable("pantallaInicioSesion") {
            PantallaInicioSesion(navHostController)
        }
    }
}
