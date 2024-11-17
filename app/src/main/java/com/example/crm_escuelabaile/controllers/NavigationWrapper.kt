package com.example.crm_escuelabaile.controllers

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.crm_escuelabaile.screens.PantallaDePrueba
import com.example.crm_escuelabaile.screens.PantallaSecundaria

@Composable
fun NavigationWrapper(navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = "pantallaDePrueba") {
        composable("pantallaDePrueba") {
            PantallaDePrueba(navHostController)
        }
        composable("pantallaSecundaria") {
            PantallaSecundaria(navHostController)
        }
    }
}
