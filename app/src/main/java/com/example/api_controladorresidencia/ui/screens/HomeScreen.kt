package com.example.api_controladorresidencia.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.api_controladorresidencia.data.ControlSesion


@Composable
fun HomeScreen(
    navController: NavController,
    controlSesion: ControlSesion
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { navController.navigate("entradas") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        ) {
            Text(text = "Ver Entradas")
        }
        Button(
            onClick = { navController.navigate("registro") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Registrar Entrada")
        }
        Button(
            onClick = { navController.navigate("list_inquilinos") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Ver Inquilinos")
        }
        Button(
            onClick = { navController.navigate("crear_inquilino") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Crear Inquilinos")
        }

        Button(onClick = {
            controlSesion.clearToken()
            navController.navigate("login") {
                popUpTo("home") { inclusive = true }
            }
        }) {
            Text("Cerrar sesión")
        }
    }
}
