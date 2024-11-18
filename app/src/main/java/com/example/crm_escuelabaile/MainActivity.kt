package com.example.crm_escuelabaile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.crm_escuelabaile.controllers.NavigationWrapper
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

class MainActivity : ComponentActivity() {
    private lateinit var navHostController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            navHostController = rememberNavController()
            NavigationWrapper(navHostController)
        }
    }
    val supabase = createSupabaseClient(
        supabaseUrl = "https://dpuzvhcgdzwbyboywjpr.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImRwdXp2aGNnZHp3Ynlib3l3anByIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzE5MTY4MTIsImV4cCI6MjA0NzQ5MjgxMn0.C2wnB58ZUgNfx1G7Rr0Yu6PhPh_jxg80GCqCsyhOul8"
    ) {
        install(Postgrest)
    }
}