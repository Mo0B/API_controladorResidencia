package com.example.api_controladorresidencia.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.api_controladorresidencia.ui.screens.EntradasScreen
import com.example.api_controladorresidencia.ui.screens.HomeScreen
import com.example.api_controladorresidencia.ui.screens.EntradasFormScreen

@Composable
fun NavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("entradas") { EntradasScreen(navController) }
        composable("registro") {
            EntradasFormScreen(navController)
        }

    }
}
