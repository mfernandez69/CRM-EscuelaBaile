package com.example.crm_escuelabaile.screens

import NotificacionViewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.crm_escuelabaile.models.Notificacion
import java.time.format.DateTimeFormatter
import java.util.Date

@Composable
fun PantallaPrincipal(
    navController: NavHostController,
    notificacionViewModel: NotificacionViewModel = viewModel()
) {
    val notificaciones by notificacionViewModel.notificaciones.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Informe general de las notificaciones",
            fontWeight = FontWeight.ExtraBold
        )

        if (notificaciones.isEmpty()) {
            Text("Cargando notificaciones o lista vacía...", Modifier.padding(16.dp))
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                items(notificaciones) { notificacion ->
                    NotificacionItem(notificacion)
                }
            }
        }
    }
}
@Composable
fun NotificacionItem(notificacion: Notificacion) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Aquí puedes agregar los detalles de la notificación
            // Por ejemplo:
            Text(text = notificacion.titulo ?: "Sin título")
            Text(text = notificacion.descripcion ?: "Sin descripción")
            Text(text = notificacion.email ?: "Sin título")
            Text(text = notificacion.telefono ?: "Sin descripción")
            Text(
                text = "Fecha: ${notificacion.fecha?.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) ?: "Sin fecha"}"
            )
        }
    }
}