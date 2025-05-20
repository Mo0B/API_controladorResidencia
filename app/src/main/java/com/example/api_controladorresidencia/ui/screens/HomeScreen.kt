package com.example.api_controladorresidencia.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = { navController.navigate("entradas") }, modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)) {
            Text(text = "Ver Entradas")
        }
        Button(onClick = { navController.navigate("registro") }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Registrar Entrada")
        }
    }
}