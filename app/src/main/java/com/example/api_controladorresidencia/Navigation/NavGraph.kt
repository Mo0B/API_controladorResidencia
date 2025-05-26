package com.example.api_controladorresidencia.Navigation

import android.util.Log
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
import com.example.api_controladorresidencia.data.repository.EntradaR
import com.example.api_controladorresidencia.data.repository.InquilinoR
import com.example.api_controladorresidencia.data.repository.LoginR
import com.example.api_controladorresidencia.data.repository.VigilanteR
import com.example.api_controladorresidencia.ui.screens.ActualizarInquilinoScreen
import com.example.api_controladorresidencia.ui.screens.CrearInquilinoScreen
import com.example.api_controladorresidencia.ui.screens.EntradasScreen
import com.example.api_controladorresidencia.ui.screens.HomeScreen
import com.example.api_controladorresidencia.ui.screens.EntradasFormScreen
import com.example.api_controladorresidencia.ui.screens.LoginScreen

import com.example.api_controladorresidencia.ui.screens.ObtenerInquilinosScreen
import com.example.api_controladorresidencia.viewmodel.EntradaViewModel
import com.example.api_controladorresidencia.viewmodel.EntradaViewModelFactory
import com.example.api_controladorresidencia.viewmodel.InquilinoViewModel
import com.example.api_controladorresidencia.viewmodel.InquilinoViewModelFactory
import com.example.api_controladorresidencia.viewmodel.LoginViewModel
import com.example.api_controladorresidencia.viewmodel.LoginViewModelFactory
import com.example.api_controladorresidencia.viewmodel.VigilanteViewModel
import com.example.api_controladorresidencia.viewmodel.VigilanteViewModelFactory

@Composable

fun NavGraph(navController: NavHostController = rememberNavController()) {
    val context = LocalContext.current
    val controlSesion = remember { ControlSesion(context) }

    val repositoryLogin = remember { LoginR(RetrofitClient.create(controlSesion)) }

    val loginViewModel: LoginViewModel = viewModel(
        factory = LoginViewModelFactory(repositoryLogin, controlSesion, VigilanteR(RetrofitClient.create(controlSesion), controlSesion))
    )

    NavHost(navController = navController, startDestination = "login") {
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
            val apiService = remember { RetrofitClient.create(controlSesion) }
            val entradaR = remember { EntradaR(apiService) }
            val factory = EntradaViewModelFactory(entradaR, controlSesion)
            val viewModel: EntradaViewModel = viewModel(factory = factory)
            EntradasScreen(navController = navController, viewModel = viewModel)
        }

        composable("registro") {

            val apiService = remember { RetrofitClient.create(controlSesion) }

            val entradaR = remember { EntradaR(apiService) }
            val entradaViewModel: EntradaViewModel = viewModel(
                factory = EntradaViewModelFactory(entradaR, controlSesion)
            )

            val vigilanteRepository = remember { VigilanteR(apiService, controlSesion) }
            val vigilanteViewModel: VigilanteViewModel = viewModel(
                factory = VigilanteViewModelFactory(vigilanteRepository, controlSesion)
            )

            val inquilinoReposiry = remember { InquilinoR(apiService) }
            val inquilinoViewModel: InquilinoViewModel = viewModel(
                factory = InquilinoViewModelFactory(inquilinoReposiry)
            )

            EntradasFormScreen(
                navController = navController,
                entradasViewModel = entradaViewModel,
                vigilanteViewModel = vigilanteViewModel,
                inquilinoViewModel = inquilinoViewModel
            )
        }

        composable("list_inquilinos") {
            val apiService = remember { RetrofitClient.create(controlSesion) }
            val inquilinoR = remember { InquilinoR(apiService) }
            val inquilinoViewModel: InquilinoViewModel = viewModel(
                factory = InquilinoViewModelFactory(inquilinoR)
            )

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
            val apiService = remember { RetrofitClient.create(controlSesion) }
            val inquilinoR = remember { InquilinoR(apiService) }
            val inquilinoViewModel: InquilinoViewModel = viewModel(
                factory = InquilinoViewModelFactory(inquilinoR)
            )

            ActualizarInquilinoScreen(
                id = id,
                viewModel = inquilinoViewModel,
                onVolver = { navController.popBackStack() }
            )
        }
    }
}


