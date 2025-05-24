package com.example.api_controladorresidencia.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.api_controladorresidencia.data.ControlSesion
import com.example.api_controladorresidencia.data.network.RetrofitClient
import com.example.api_controladorresidencia.data.repository.LoginR
import com.example.api_controladorresidencia.ui.screens.ActualizarInquilinoScreen
import com.example.api_controladorresidencia.ui.screens.CrearInquilinoScreen
import com.example.api_controladorresidencia.ui.screens.EntradasScreen
import com.example.api_controladorresidencia.ui.screens.HomeScreen
import com.example.api_controladorresidencia.ui.screens.EntradasFormScreen
import com.example.api_controladorresidencia.ui.screens.LoginScreen

import com.example.api_controladorresidencia.ui.screens.ObtenerInquilinosScreen
import com.example.api_controladorresidencia.viewmodel.InquilinoViewModel
import com.example.api_controladorresidencia.viewmodel.LoginViewModel
import com.example.api_controladorresidencia.viewmodel.LoginViewModelFactory

@Composable

fun NavGraph(navController: NavHostController = rememberNavController())
{


    val inquilinoViewModel: InquilinoViewModel = viewModel()
    val context = LocalContext.current
    val controlSesion = remember { ControlSesion(context) }
    val repository = remember { LoginR(RetrofitClient.instancia) }
    val loginViewModel: LoginViewModel = viewModel(
        factory = LoginViewModelFactory(repository, controlSesion)
    )

    NavHost(navController = navController, startDestination = "login")
    {

        composable("login") {
            LoginScreen(
                viewModel = loginViewModel,
                onLoginSuccess = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        composable("home") {
            HomeScreen(navController = navController, controlSesion = controlSesion)
        }

        composable("entradas") {
            EntradasScreen(navController)
        }

        composable("registro") {
            EntradasFormScreen(navController)
        }

        composable("list_inquilinos") {
            ObtenerInquilinosScreen(
                viewModel = inquilinoViewModel,
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
            ActualizarInquilinoScreen(
                id = id,
                viewModel = inquilinoViewModel,
                onVolver = { navController.popBackStack() }
            )
        }
    }
}

