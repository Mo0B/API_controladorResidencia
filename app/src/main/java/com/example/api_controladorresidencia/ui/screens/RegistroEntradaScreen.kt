package com.example.api_controladorresidencia.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntradasFormScreen(navController: NavController) {
    var categoria by remember { mutableStateOf("Inquilino") }
    var referencia by remember { mutableStateOf("") }
    var idVigilante by remember { mutableStateOf("") }
    var isDropdownExpanded by remember { mutableStateOf(false) }

    val categorias = listOf("Inquilino", "Visitante", "Mantenimiento", "Obra")

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Registrar Entrada") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Campo para ID del vigilante
            OutlinedTextField(
                value = idVigilante,
                onValueChange = { idVigilante = it },
                label = { Text("ID del Vigilante") },
                modifier = Modifier.fillMaxWidth()
            )

            // Menú desplegable para categoría
            ExposedDropdownMenuBox(
                expanded = isDropdownExpanded,
                onExpandedChange = { isDropdownExpanded = !isDropdownExpanded }
            ) {
                OutlinedTextField(
                    readOnly = true,
                    value = categoria,
                    onValueChange = {},
                    label = { Text("Categoría") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isDropdownExpanded) },
                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = isDropdownExpanded,
                    onDismissRequest = { isDropdownExpanded = false }
                ) {
                    categorias.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                categoria = option
                                isDropdownExpanded = false
                            }
                        )
                    }
                }
            }

            // Campo para ID de la referencia según la categoría
            OutlinedTextField(
                value = referencia,
                onValueChange = { referencia = it },
                label = {
                    Text("ID de ${categoria}")
                },
                modifier = Modifier.fillMaxWidth()
            )

            // Botón para enviar (por ahora aún no conectado al backend)
            Button(
                onClick = {
                    // En una próxima fase conectaremos esta lógica con la API.
                    println("Registrando entrada con: Categoria=$categoria, Referencia=$referencia, Vigilante=$idVigilante")
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrar Entrada")
            }
        }
    }
}

