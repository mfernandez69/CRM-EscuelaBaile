import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crm_escuelabaile.models.Notificacion
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class NotificacionViewModel : ViewModel() {
    private val _notificaciones = MutableStateFlow<List<Notificacion>>(emptyList())
    val notificaciones: StateFlow<List<Notificacion>> = _notificaciones

    init {
        Log.d("NotificacionViewModel", "Inicializando ViewModel")
        cargarNotificaciones()
    }

    private fun cargarNotificaciones() {
        viewModelScope.launch {
            try {
                val notificacionesObtenidas = getNotificaciones()
                _notificaciones.value = notificacionesObtenidas
                Log.d("NotificacionViewModel", "Notificaciones cargadas: ${notificacionesObtenidas.size}")
            } catch (e: Exception) {
                Log.e("NotificacionViewModel", "Error al cargar notificaciones", e)
            }
        }
    }

    private suspend fun getNotificaciones(): List<Notificacion> {
        //Esta funcion realiza una peticion a firebase y devuelve una Lista con los objetos Notificacion
        val db = FirebaseFirestore.getInstance()
        val coleccion = db.collection("notificacion")

        return try {
            val querySnapshot = coleccion.get().await()
            //Igualamos una array a los resultados obtenidos transformandolos en objetos Notificacion
            val notificaciones = querySnapshot.toObjects(Notificacion::class.java)
            Log.d("NotificacionViewModel", "Notificaciones obtenidas de Firestore: ${notificaciones.size}")
            notificaciones
        } catch (e: Exception) {
            Log.e("NotificacionViewModel", "Error al obtener notificaciones de Firestore", e)
            emptyList()
        }
    }
}