import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crm_escuelabaile.models.Notificacion
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class NotificacionViewModel : ViewModel() {
    val auth: FirebaseAuth = Firebase.auth
    private val _notificaciones = MutableStateFlow<List<Notificacion>>(emptyList())
    val notificaciones: StateFlow<List<Notificacion>> = _notificaciones

    private val _notificacionesNoLeidas = MutableStateFlow<List<Notificacion>>(emptyList())
    val notificacionesNoLeidas: StateFlow<List<Notificacion>> = _notificacionesNoLeidas

    private val _notificacionesLeidas = MutableStateFlow<List<Notificacion>>(emptyList())
    val notificacionesLeidas: StateFlow<List<Notificacion>> = _notificacionesLeidas

    private val _cantidadNotificacionesNoLeidas = MutableStateFlow(0)
    val cantidadNotificacionesNoLeidas: StateFlow<Int> = _cantidadNotificacionesNoLeidas

    init {
        Log.d("NotificacionViewModel", "Inicializando ViewModel")
        cargarNotificaciones()
    }

    private fun cargarNotificaciones() {
        viewModelScope.launch {
            try {
                val notificacionesObtenidas = getNotificaciones()
                _notificaciones.value = notificacionesObtenidas
                val noLeidas = notificacionesObtenidas.filter { !it.leida }
                _notificacionesNoLeidas.value = noLeidas
                _notificacionesLeidas.value = notificacionesObtenidas.filter { it.leida }
                _cantidadNotificacionesNoLeidas.value = noLeidas.size
                Log.d(
                    "NotificacionViewModel",
                    "Notificaciones cargadas: ${notificacionesObtenidas.size}"
                )
                Log.d("NotificacionViewModel", "Notificaciones no leídas: ${noLeidas.size}")
            } catch (e: Exception) {
                Log.e("NotificacionViewModel", "Error al cargar notificaciones", e)
            }
        }
    }

    private suspend fun getNotificaciones(): List<Notificacion> {
        //Esta funcion realiza una peticion a firebase y devuelve una Lista con los objetos Notificacion
        val user = auth.currentUser
        val email = user?.email
        val db = FirebaseFirestore.getInstance()
        val coleccion = db.collection("notificacion")

        return try {
            if (email != null) {
                //Obtenemos las notificaciones asignadas al administrador
                val query = coleccion.whereEqualTo("emailDestinatario", email)
                val querySnapshot = query.get().await()
                //Las notificaciones tranasformadas a objetos se ordenan por su fecha
                val notificaciones = querySnapshot.toObjects(Notificacion::class.java)
                    .sortedByDescending { it.fecha }

                Log.d("NotificacionViewModel", "Notificaciones obtenidas: ${notificaciones.size}")
                notificaciones
            } else {
                Log.e("NotificacionViewModel", "Email del usuario es nulo")
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("NotificacionViewModel", "Error al obtener notificaciones", e)
            emptyList()
        }
    }

    public fun marcarLeido(notificacion: Notificacion) {
        val db = FirebaseFirestore.getInstance()
        val coleccion = db.collection("notificacion")
        try {
            val notificacionDocument = coleccion.document(notificacion.id)

            if (notificacion.leida) {
                notificacion.leida = false
                notificacionDocument.update("leida", false)
            } else {
                notificacion.leida = true
                notificacionDocument.update("leida", true)
            }
            cargarNotificaciones()
        } catch (e: Exception) {
            Log.e("NotificacionViewModel", "Error al obtener documento de Firestore", e)
        }
    }

    @Composable
    public fun leidaNoLeida(notificacion: Notificacion): String {
        var textoNotificacion = "";
        if (notificacion.leida) {
            textoNotificacion = "Marcar como no leida"
        } else {
            textoNotificacion = "Marcar como leida"
        }
        return textoNotificacion
    }

}