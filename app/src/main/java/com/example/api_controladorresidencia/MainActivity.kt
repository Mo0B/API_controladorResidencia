package com.example.api_controladorresidencia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.api_controladorresidencia.Navigation.NavGraph
import com.example.api_controladorresidencia.ui.theme.API_controladorResidenciaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            API_controladorResidenciaTheme {
                NavGraph(navController = rememberNavController())
            }
        }
    }
}
