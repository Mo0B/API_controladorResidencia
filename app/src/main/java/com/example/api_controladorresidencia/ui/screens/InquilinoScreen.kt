package com.example.api_controladorresidencia.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.api_controladorresidencia.data.model.InquilinoM
import com.example.api_controladorresidencia.viewmodel.InquilinoViewModel



@Composable
fun ObtenerInquilinosScreen(
    viewModel: InquilinoViewModel,
    onCrearInquilinoClick: () -> Unit,
    onEditarInquilinoClick: (Long) -> Unit
) {
    val inquilinos by viewModel.inquilino.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.getInquilinoS()
    }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onCrearInquilinoClick) {
                Icon(Icons.Default.Add, contentDescription = "Agregar Inquilino")
            }
        }
    ) { padding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(padding)) {

            when {
                isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                error != null -> {
                    Text(
                        text = error ?: "Error desconocido",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                inquilinos.isEmpty() -> {
                    Text(
                        text = "No hay inquilinos registrados",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                else -> {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(inquilinos) { inquilino ->
                            InquilinoItem(inquilino = inquilino, onClick = {
                                // Validar que el ID no sea nulo
                                inquilino.id?.let { id ->
                                    onEditarInquilinoClick(id)
                                }
                            })
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun InquilinoItem(inquilino: InquilinoM, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(text = "Codigo: ${inquilino.id}", style = MaterialTheme.typography.bodySmall)
            Text(text = "Nombre: ${inquilino.nombre}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Apellido: ${inquilino.apellido}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Telefono: ${inquilino.telefono}", style = MaterialTheme.typography.bodySmall)
            Text(text = "Numero de Documento: ${inquilino.num_Documento}", style = MaterialTheme.typography.bodySmall)
            Text(text = "Numero de Torre: ${inquilino.num_Torre}", style = MaterialTheme.typography.bodySmall)
            Text(text = "Numero de Apartamento: ${inquilino.num_Apartamento}", style = MaterialTheme.typography.bodySmall)

        }
    }
}
////////////////////////////////////////////////////////////////////////////////////////////////
@Composable
fun CrearInquilinoScreen(
    viewModel: InquilinoViewModel = viewModel(),
    onGuardarExitosa: () -> Unit,
    onCancelar: () -> Unit
) {
    val nombreState = remember { mutableStateOf("") }
    val apellidoState = remember { mutableStateOf("") }
    val telefonoState = remember { mutableStateOf("") }
    val num_documentoState = remember { mutableStateOf("") }
    val num_torreState = remember { mutableStateOf("") }
    val num_apartamentoState = remember { mutableStateOf("") }
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(text = "Crear Inquilino", style = MaterialTheme.typography.headlineMedium)



        OutlinedTextField(
            value = nombreState.value,
            onValueChange = { nombreState.value = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = apellidoState.value,
            onValueChange = { apellidoState.value = it },
            label = { Text("Apellido") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = telefonoState.value,
            onValueChange = { telefonoState.value = it },
            label = { Text("Telefono") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = num_documentoState.value,
            onValueChange = { num_documentoState.value = it },
            label = { Text("Numero de documento") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = num_torreState.value,
            onValueChange = { num_torreState.value = it },
            label = { Text("Numero de la Torre") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = num_apartamentoState.value,
            onValueChange = { num_apartamentoState.value = it },
            label = { Text("Numero de Apartamento") },
            modifier = Modifier.fillMaxWidth()
        )



        if (!error.isNullOrEmpty()) {
            Text(text = error ?: "", color = MaterialTheme.colorScheme.error)
        }

        if (isLoading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = onCancelar) {
                Text("Cancelar")
            }

            Button(
                onClick = {
                    val nuevoInquilino = InquilinoM(
                        //id = 0, autogenerado en backend
                        nombre = nombreState.value.trim(),
                        apellido = apellidoState.value.trim(),
                        telefono = telefonoState.value.trim(),
                        num_Documento = num_documentoState.value.trim(),
                        num_Torre = num_torreState.value.trim(),
                        num_Apartamento = num_apartamentoState.value.trim()
                    )
                    viewModel.saveInquilino(nuevoInquilino,
                        onSuccess = onGuardarExitosa,
                        onError = { mensaje -> viewModel.setError(mensaje) }
                    )
                },
                enabled = nombreState.value.isNotBlank() && !isLoading
            ) {
                Text("Guardar")
            }
        }
    }
}
////////////////////////////////////////////////////////////////////////////////////////////////////
@Composable
fun ActualizarInquilinoScreen(id: Long, viewModel: InquilinoViewModel, onVolver: () -> Unit) {

    val inquilinos by viewModel.inquilino.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    val inquilino = inquilinos.find { it.id == id }

    var nombre by remember(inquilino) { mutableStateOf(inquilino?.nombre ?: "") }
    var apellido by remember(inquilino) { mutableStateOf(inquilino?.apellido ?: "") }
    var telefono by remember(inquilino) { mutableStateOf(inquilino?.telefono ?: "") }
    var documento by remember(inquilino) { mutableStateOf(inquilino?.num_Documento ?: "") }
    var torre by remember(inquilino) { mutableStateOf(inquilino?.num_Torre ?: "") }
    var apartamento by remember(inquilino) { mutableStateOf(inquilino?.num_Apartamento ?: "") }
    var errorMessage by remember(inquilino) { mutableStateOf<String?>(null) }
    var isSaving by remember(inquilino) { mutableStateOf(false) }

    if (inquilino != null) {
        Column(Modifier.padding(16.dp)) {
            Text("Editando Inquilino: ${inquilino.nombre}")



            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = apellido,
                onValueChange = { apellido = it },
                label = { Text("Apellido") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = telefono,
                onValueChange = { telefono = it },
                label = { Text("Teléfono") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = documento,
                onValueChange = { documento = it },
                label = { Text("Numero de documento") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = torre,
                onValueChange = { torre = it },
                label = { Text("Numero de Torre") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = apartamento,
                onValueChange = { apartamento = it },
                label = { Text("Numero del Apartamento") },
                modifier = Modifier.fillMaxWidth()
            )



            Spacer(modifier = Modifier.height(16.dp))
            if (errorMessage != null) {
                Text(text = errorMessage!!, color = Color.Red)
                Spacer(modifier = Modifier.height(8.dp))
            }

                Button(onClick = {
                    isSaving = true
                    errorMessage = null

                    val actualizado = inquilino.copy(
                        nombre = nombre,
                        telefono = telefono,
                        apellido = apellido,
                        num_Torre = torre,
                        num_Apartamento = apartamento,
                        num_Documento = documento
                    )

                            viewModel.updateInquilino(
                                viewModel = viewModel,
                                id = inquilino.id,
                                nuevoInquilino = actualizado,
                                onSuccess = {
                                    isSaving = false
                                    onVolver()
                                },
                                onError = { error ->
                                    isSaving = false
                                    errorMessage = "Error al actualizar: $error"
                                }
                            )


            }, enabled = !isSaving,
                    modifier = Modifier.fillMaxWidth()) {
                    Text(if (isSaving) "Guardando..." else "Guardar Cambios")

            }
        }
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Cargando o no encontrado")
        }
    }
}



