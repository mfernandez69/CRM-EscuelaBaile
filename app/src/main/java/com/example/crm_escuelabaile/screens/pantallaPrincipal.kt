package com.example.crm_escuelabaile.screens

import NotificacionViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.crm_escuelabaile.R
import com.example.crm_escuelabaile.controllers.LogicaInicioSesion
import com.example.crm_escuelabaile.controllers.LogicaMenu
import com.example.crm_escuelabaile.models.Notificacion
import com.example.crm_escuelabaile.utils.MenuLateral
import java.text.SimpleDateFormat
import java.util.Locale
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaPrincipal(
    navController: NavHostController,
    notificacionViewModel: NotificacionViewModel = viewModel(),
    logicaMenu: LogicaMenu = viewModel(),
    logicaInicioSesion: LogicaInicioSesion = viewModel()
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    MenuLateral(
        navController = navController,
        drawerState = drawerState,
        logicaMenu = logicaMenu,
        logicaInicioSesion= logicaInicioSesion
    ) { paddingValues ->
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text("Notificaciones") },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.open()
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = "Abrir el menu"
                            )
                        }
                    }
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Spacer(modifier = Modifier.size(5.dp))
                NotificacionesTabLayout(notificacionViewModel)
            }
        }
    }
}

@Composable
fun NotificacionesTabLayout(notificacionViewModel: NotificacionViewModel) {
    //La pantalla principal tiene dos estados (las opciones del tab estan enlazadas con cada estado)
    val pagerState = rememberPagerState(pageCount = { 3 })
    val cantidadNoLeidas by notificacionViewModel.cantidadNotificacionesNoLeidas.collectAsState()
    val cantidadNotificaciones by notificacionViewModel.cantidadNotificaciones.collectAsState()
    Column {
        Box(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .shadow(4.dp, RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp))
        ){
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                contentColor = Color.White,
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp)) // Bordes redondeados
                    .shadow(4.dp, RoundedCornerShape(12.dp)), // Sombreado
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier
                            .tabIndicatorOffset(tabPositions[pagerState.currentPage])
                            .height(4.dp) // Grosor de la barra
                            .clip(RoundedCornerShape(12.dp)), // Bordes redondeados para la barra
                        color = Color(0xFFFD667E) // Cambia el color a tu preferencia
                    )
                }
            ) {
                val scope = rememberCoroutineScope()
                Tab(
                    selected = pagerState.currentPage == 0,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(0)
                        }
                    },

                    ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()

                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "No leídos",
                                color = Color.Black,
                                fontWeight = FontWeight.Bold
                            )
                            if (cantidadNoLeidas > 0) {
                                Box(
                                    modifier = Modifier
                                        .padding(start = 4.dp)
                                        .size(24.dp)
                                        .background(Color.Red, CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = cantidadNoLeidas.toString(),
                                        color = Color.White,
                                        fontSize = 12.sp
                                    )
                                }
                            }
                        }
                    }
                }

                // leidos
                Tab(
                    selected = pagerState.currentPage == 1,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(1)
                        }
                    }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()

                            .padding(8.dp), // Espaciado interno
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Leídos",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // tab de todos
                Tab(
                    selected = pagerState.currentPage == 2,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(2)
                        }
                    }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize() // Llenar todo el espacio disponible del Tab

                            .padding(8.dp), // Espaciado interno para el contenido
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Todos",
                                color = Color.Black,
                                fontWeight = FontWeight.Bold
                            )
                            Box(
                                modifier = Modifier
                                    .padding(start = 4.dp)
                                    .size(24.dp)
                                    .background(Color.Red, CircleShape), // Indicador circular rojo
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = cantidadNotificaciones.toString(),
                                    color = Color.White,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }

            }

        }

        HorizontalPager(state = pagerState) { page ->
            when (page) {
                //Definimos que funciones se pintan dependiendo del estado del tab
                0 -> NotificacionesNoLeidasScreen(notificacionViewModel)
                1 -> NotificacionesLeidasScreen(notificacionViewModel)
                2 -> NotificacionesTodasScreen(notificacionViewModel)
            }
        }
    }
}

@Composable
fun NotificacionesNoLeidasScreen(notificacionViewModel: NotificacionViewModel) {
    val notificacionesNoLeidas by notificacionViewModel.notificacionesNoLeidas.collectAsState()

    if (notificacionesNoLeidas.isEmpty()) {
        Text("No hay notificaciones no leídas", Modifier.padding(16.dp))
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(notificacionesNoLeidas) { notificacion ->
                NotificacionItem(notificacion, notificacionViewModel)
            }
        }
    }
}

@Composable
fun NotificacionesLeidasScreen(notificacionViewModel: NotificacionViewModel) {
    val notificacionesLeidas by notificacionViewModel.notificacionesLeidas.collectAsState()

    if (notificacionesLeidas.isEmpty()) {
        Text("No hay notificaciones leídas", Modifier.padding(16.dp))
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(notificacionesLeidas) { notificacion ->
                NotificacionItem(notificacion, notificacionViewModel)
            }
        }
    }
}

@Composable
fun NotificacionesTodasScreen(notificacionViewModel: NotificacionViewModel){
    val notificacionesTodas by notificacionViewModel.notificaciones.collectAsState()
    if (notificacionesTodas.isEmpty()) {
        Text("No hay notificaciones", Modifier.padding(16.dp))
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(notificacionesTodas) { notificacion ->
                NotificacionItem(notificacion, notificacionViewModel)
            }

        }
    }
}

@Composable
fun NotificacionItem(notificacion: Notificacion, notificacionViewModel: NotificacionViewModel) {
    //Creamos una card para cada notificacion con la info del objeto
    var elegirColorNotificacion = colorLeidaNoLeida(notificacion)
    var iconoBoton = notificacionViewModel.leidaNoLeida(notificacion)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .shadow(4.dp, shape = RoundedCornerShape(8.dp)) // Sombra personalizada
            .background(Color.Transparent),
        colors = CardColors(
            contentColor = Color.Black,
            containerColor = elegirColorNotificacion,
            disabledContentColor = Color.Unspecified,
            disabledContainerColor = Color.Unspecified
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = notificacion.titulo ?: "Sin título",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
                )
            Text(text = notificacion.descripcion ?: "Sin descripción",
                fontSize = 15.sp)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = notificacion.email ?: "Sin email")
                IconButton(
                    onClick = {
                        notificacionViewModel.marcarLeido(notificacion)

                    }
                ) {
                    Icon(
                        painter = painterResource(id = iconoBoton),
                        contentDescription = "Icono de leido",
                        tint = Color.Black
                    )
                }
            }
        }
    }

}

fun colorLeidaNoLeida(notificacion: Notificacion): Color {

    var colorNotificacion = Color.White

    if(notificacion.leida){
        colorNotificacion = Color(0xFFEEEEEE)
    }
    else{
        colorNotificacion = Color(0xFFFFFFFF)

    }
    return colorNotificacion
}