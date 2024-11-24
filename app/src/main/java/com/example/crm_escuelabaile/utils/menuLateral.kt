package com.example.crm_escuelabaile.utils


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.ui.Modifier
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import com.example.crm_escuelabaile.R
import com.example.crm_escuelabaile.controllers.LogicaInicioSesion
import com.example.crm_escuelabaile.controllers.LogicaMenu
import kotlinx.coroutines.launch

val colorPrimario = Color(0xFF30C67C)
val colorSegundario = Color(0xFF82F4B1)
val colorGris = Color(0xFF3A3737)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuLateral(
    navController: NavHostController,
    drawerState: DrawerState,
    logicaMenu: LogicaMenu,
    logicaInicioSesion: LogicaInicioSesion,
    content: @Composable (PaddingValues) -> Unit
) {
    val scope = rememberCoroutineScope()
    var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }
    var searchText by remember { mutableStateOf("") }
    val adminData by logicaMenu.adminData.collectAsState()

    LaunchedEffect(Unit) {
        logicaMenu.recogerDatosAdmin()
    }
    val items = listOf(
        NavigationItem(
            title = "Inicio",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home
        ),
        NavigationItem(
            title = "Pagos",
            selectedIcon = Icons.Filled.Person,
            unselectedIcon = Icons.Outlined.Person
        ),
        NavigationItem(
            title = "Agenda",
            selectedIcon = Icons.Filled.DateRange,
            unselectedIcon = Icons.Outlined.DateRange
        ),
        NavigationItem(
            title = "Alumnos",
            selectedIcon = Icons.Filled.Face,
            unselectedIcon = Icons.Outlined.Face
        ),
        NavigationItem(
            title = "Ajustes",
            selectedIcon = Icons.Filled.Build,
            unselectedIcon = Icons.Outlined.Build
        ),
        NavigationItem(
            title = "Cerrar sesión",
            selectedIcon = Icons.Filled.ExitToApp,
            unselectedIcon = Icons.Outlined.ExitToApp
        )
    )

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = Color.White,
                modifier = Modifier
                    .width(300.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .border(
                        BorderStroke(1.dp, colorPrimario),
                        shape = RoundedCornerShape(8.dp)
                    )
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                PerfilContainer(
                    nombre = adminData.nombre,
                    email = adminData.email,

                    )
                // Buscador
                OutlinedTextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .background(Color.LightGray.copy(alpha = 0.2f), RoundedCornerShape(8.dp)),
                    placeholder = { Text("Buscar") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar") },
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                items.forEachIndexed { index, item ->
                    NavigationDrawerItem(
                        label = { Text(text = item.title, color = colorPrimario) },
                        selected = index == selectedItemIndex,
                        onClick = {
                            selectedItemIndex = index
                            scope.launch {
                                drawerState.close()
                            }
                            when (index) {
                                0 -> navController.navigate("pantallaPrincipal")
                                1 -> navController.navigate("")
                                2 -> navController.navigate("pantallaAgenda")
                                3 -> navController.navigate("")
                                4 -> navController.navigate("")
                                5 -> {
                                    logicaInicioSesion.cerrarSesion()
                                    navController.navigate("pantallaInicioSesion") {
                                        //Evitamos que el usuario pueda volver a pantallas que requieren autentificacion
                                        popUpTo(navController.graph.startDestinationId) {
                                            inclusive = true
                                        }
                                    }
                                }
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = if (index == selectedItemIndex) {
                                    item.selectedIcon
                                } else item.unselectedIcon,
                                contentDescription = item.title,
                                tint = colorPrimario
                            )
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedContainerColor = Color.Transparent,
                            unselectedContainerColor = Color.Transparent
                        )
                    )
                }
            }
        },
        drawerState = drawerState
    ) {
        content(PaddingValues())
    }
}

@Composable
fun PerfilContainer(nombre: String, email: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.imgperfil),
                contentDescription = "Foto de perfil del administrador",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .border(2.dp, colorPrimario, CircleShape)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = nombre,
                    style = MaterialTheme.typography.titleMedium,
                    color = colorPrimario
                )
                Text(
                    text = email,
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorPrimario.copy(alpha = 0.7f)
                )
            }
        }
    }
}

//Creamos una clase para cada opcion del menu lateral
data class NavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badgeCount: Int? = null
)