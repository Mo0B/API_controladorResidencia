package com.example.api_controladorresidencia.Navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.api_controladorresidencia.ui.screens.ActualizarInquilinoScreen
import com.example.api_controladorresidencia.ui.screens.CrearInquilinoScreen
import com.example.api_controladorresidencia.ui.screens.EntradasScreen
import com.example.api_controladorresidencia.ui.screens.HomeScreen
import com.example.api_controladorresidencia.ui.screens.EntradasFormScreen

import com.example.api_controladorresidencia.ui.screens.ObtenerInquilinosScreen
import com.example.api_controladorresidencia.viewmodel.InquilinoViewModel

@Composable
fun NavGraph(navController: NavHostController = rememberNavController()) {

    val viewModel: InquilinoViewModel = viewModel()
    NavHost(navController = navController, startDestination = "home") {

        composable("home") { HomeScreen(navController) }

        composable("entradas") { EntradasScreen(navController) }

        composable("registro") {
            EntradasFormScreen(navController)
        }

        composable("list_inquilinos") {
            ObtenerInquilinosScreen(
                viewModel = viewModel,
                onCrearInquilinoClick = {
                    navController.navigate("crear_inquilino")
                },
                onEditarInquilinoClick = { id ->
                    navController.navigate("edit_inquilino/$id")

                }
            )
        }

        composable("crear_inquilino") {
            CrearInquilinoScreen(
                onGuardarExitosa = { navController.popBackStack() },
                onCancelar = { navController.popBackStack() }
            )
        }

        composable(
            route = "edit_inquilino/{id}",
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong("id") ?: 0L
            ActualizarInquilinoScreen(id = id, viewModel = viewModel, onVolver = { navController.popBackStack() })
        }



    }
}
