package com.example.crm_escuelabaile.controllers

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.crm_escuelabaile.screens.*

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
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
        composable("pantallaCodigo") {
            PantallaCodigo(navHostController)
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
        composable("pantallaAlumnos") {
            PantallaAlumnos(navHostController)
        }
    }
}
