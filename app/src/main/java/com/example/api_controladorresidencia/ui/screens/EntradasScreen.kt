package com.example.api_controladorresidencia.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.api_controladorresidencia.data.model.InquilinoM
import com.example.api_controladorresidencia.viewmodel.EntradaViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@Composable
fun EntradasScreen(
    navController: NavController,
    viewModel: EntradaViewModel
) {

    Column(modifier = Modifier.statusBarsPadding())
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
                                    Text(text = "Razon: ${entrada.perVisitante.razon}")
                                }

                                entrada.perInquilino != null -> {
                                    Text(text = "Inquilino: ${entrada.perInquilino.nombre} ${entrada.perInquilino.apellido}")
                                    Text(text = "Numero Torre: ${entrada.perInquilino.num_Torre} ")
                                    Text(text = "Apartamento: ${entrada.perInquilino.num_Apartamento}")
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

