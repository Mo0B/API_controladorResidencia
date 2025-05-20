package com.example.api_controladorresidencia.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.api_controladorresidencia.viewmodel.EntradaViewModel

@Composable
fun EntradasScreen(
    navController: NavController,
    viewModel: EntradaViewModel = viewModel()
) {

    Column(modifier = Modifier.statusBarsPadding())  //Evitemos que se nos ponga debajo de la barra de notificaciones por favor
    {
        val entradas = viewModel.entradas.collectAsState()
        val isLoading = viewModel.isLoading.collectAsState()
        val error = viewModel.error.collectAsState()

        if (isLoading.value) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (error.value != null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = error.value ?: "Error desconocido")
            }
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(entradas.value) { entrada ->
                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = "ID: ${entrada.id}")
                            Text(text = "Categoría: ${entrada.categoria}")
                            Text(text = "Fecha Entrada: ${entrada.fecha_entrada}")
                            Text(text = "Fecha Salida: ${entrada.fecha_salida ?: "Aún dentro"}")
                            Text(text = "Referencia: ${entrada.referencia ?: "N/A"}")
                            Text(
                                text = "Vigilante: ${entrada.vigilante_reg?.nombre ?: "Desconocido"} " +
                                        "${entrada.vigilante_reg?.apellido ?: ""}"
                            )

                            when {
                                entrada.perVisitante != null -> {
                                    Text(text = "Visitante: ${entrada.perVisitante.nombre} ${entrada.perVisitante.apellido}")
                                }
                                entrada.perInquilino != null -> {
                                    Text(text = "Inquilino: ${entrada.perInquilino.nombre} ${entrada.perInquilino.apellido}")
                                }
                                entrada.perMantenimiento != null -> {
                                    Text(text = "Mantenimiento: ${entrada.perMantenimiento.nombre} ${entrada.perMantenimiento.apellido}")
                                }
                                entrada.perObra != null -> {
                                    Text(text = "Obrero: ${entrada.perObra.nombre} ${entrada.perObra.apellido}")
                                }
                                else -> {
                                    Text(text = "Sin persona registrada")
                                }
                            }
                        }
                    }
                }
            }
        }
    }



}
