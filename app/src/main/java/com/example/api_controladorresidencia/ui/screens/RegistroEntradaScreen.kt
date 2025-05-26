package com.example.api_controladorresidencia.ui.screens

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.api_controladorresidencia.data.ControlSesion
import com.example.api_controladorresidencia.data.model.InquilinoM
import com.example.api_controladorresidencia.viewmodel.EntradaViewModel
import com.example.api_controladorresidencia.viewmodel.VigilanteViewModel
import java.time.LocalDateTime
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.graphics.Color
import com.example.api_controladorresidencia.viewmodel.InquilinoViewModel


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntradasFormScreen(
    navController: NavController,
    entradasViewModel: EntradaViewModel,
    vigilanteViewModel: VigilanteViewModel,
    inquilinoViewModel: InquilinoViewModel
) {
    val context = LocalContext.current
    val controlSesion = remember { ControlSesion(context) }
    val idVigilante = remember { mutableStateOf(controlSesion.getVigilanteId() ?: "") }
    val categoria = remember { mutableStateOf("Inquilino") }
    val isDropdownExpanded = remember { mutableStateOf(false) }

    val inquilinos by inquilinoViewModel.inquilino.collectAsState()
    val mostrarListaInquilinos = remember { mutableStateOf(false) }
    val filtroBusqueda = remember { mutableStateOf("") }  // Para filtrar la lista
    val inquilinoSeleccionado = remember { mutableStateOf<InquilinoM?>(null) }



    /////// COMUNES
    val nombre = remember { mutableStateOf("") }
    val apellido = remember { mutableStateOf("") }
    val cedula = remember { mutableStateOf("") }
    val telefono = remember { mutableStateOf("") }
    /////// INQUILINO
    val num_apartamento = remember { mutableStateOf("") }
    val num_torre = remember { mutableStateOf("") }
    /////// VISITANTE
    val razon_V = remember { mutableStateOf("") }
    val inquilino_v = remember { mutableStateOf("") }
    /////// MANTENIMIENTO
    val compania_M = remember { mutableStateOf("") }
    val categoria_M = remember { mutableStateOf("") }
    /////// OBRA
    val fechaEntrada = remember { mutableStateOf(LocalDateTime.now().toString()) }

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


            ExposedDropdownMenuBox(
                expanded = isDropdownExpanded.value,
                onExpandedChange = { isDropdownExpanded.value = !isDropdownExpanded.value }
            ) {
                OutlinedTextField(
                    readOnly = true,
                    value = categoria.value,
                    onValueChange = {},
                    label = { Text("Categoría") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(isDropdownExpanded.value) },
                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = isDropdownExpanded.value,
                    onDismissRequest = { isDropdownExpanded.value = false }
                ) {
                    categorias.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                categoria.value = option
                                isDropdownExpanded.value = false

                            }
                        )
                    }
                }
            }
////////////////////////////////////////////////////////////////////////////////////////////////////   COMUNES
            OutlinedTextField(
                value = nombre.value,
                onValueChange = { nombre.value = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = apellido.value,
                onValueChange = { apellido.value = it },
                label = { Text("Apellido") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = cedula.value,
                onValueChange = { cedula.value = it },
                label = { Text("Cédula") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = telefono.value,
                onValueChange = { telefono.value = it },
                label = { Text("Teléfono") },
                modifier = Modifier.fillMaxWidth()
            )
//////////////////////////////////////////////////////////////////////////////////////////////////// INQUILINOS
            if (categoria.value == "Inquilino") {
                OutlinedTextField(
                    value = num_torre.value,
                    onValueChange = { num_torre.value = it },
                    label = { Text("Número de Torre") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = num_apartamento.value,
                    onValueChange = { num_apartamento.value = it },
                    label = { Text("Número de Apartamento") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
////////////////////////////////////////////////////////////////////////////////////////////////////  VISITANTES
            if (categoria.value == "Visitante") {
                OutlinedTextField(
                    value = razon_V.value,
                    onValueChange = { razon_V.value = it },
                    label = { Text("Razon de Visita") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = filtroBusqueda.value,
                    onValueChange = {
                        filtroBusqueda.value = it
                        mostrarListaInquilinos.value = it.isNotEmpty()
                    },
                    label = { Text("Buscar Inquilino para Visitante") },
                    modifier = Modifier.fillMaxWidth()
                )

                if (mostrarListaInquilinos.value) {
                    val listaFiltrada = inquilinos.filter {
                        it.nombre.contains(filtroBusqueda.value, ignoreCase = true) ||
                                it.apellido.contains(filtroBusqueda.value, ignoreCase = true) ||
                                it.num_Documento.contains(filtroBusqueda.value)
                    }
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .background(Color.White)
                            .border(1.dp, Color.Gray)
                    ) {
                        items(listaFiltrada) { inq ->
                            DropdownMenuItem(
                                text = { Text("${inq.nombre} ${inq.apellido}") },
                                onClick = {
                                    inquilinoSeleccionado.value = inq
                                    filtroBusqueda.value = "${inq.nombre} ${inq.apellido}"
                                    mostrarListaInquilinos.value = false
                                }
                            )
                        }
                    }
                }


            }
//////////////////////////////////////////////////////////////////////////////////////////////////// MANTENIMIENTO
            if (categoria.value == "Mantenimiento") {
                OutlinedTextField(
                    value = compania_M.value,
                    onValueChange = { compania_M.value = it },
                    label = { Text("Empresa") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = categoria_M.value,
                    onValueChange = { categoria_M.value = it },
                    label = { Text("Tipo") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
////////////////////////////////////////////////////////////////////////////////////////////////////  OBRA
            if (categoria.value == "Obra") {
                OutlinedTextField(
                    value = num_torre.value,
                    onValueChange = { num_torre.value = it },
                    label = { Text("Número de Torre") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = num_apartamento.value,
                    onValueChange = { num_apartamento.value = it },
                    label = { Text("Número de Apartamento") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Button(
                onClick = {
                    if (
                        nombre.value.isBlank() ||
                        apellido.value.isBlank() ||
                        cedula.value.isBlank() ||
                        telefono.value.isBlank()
                    ) { Toast.makeText(context, "CAMPOS VACIOS 1", Toast.LENGTH_SHORT).show()    } else {
                        if (categoria.value == "Inquilino") {
                            if (
                                num_torre.value.isBlank() ||
                                num_apartamento.value.isBlank()
                            ) {
                                Toast.makeText(context, "CAMPOS VACIOS 2", Toast.LENGTH_SHORT).show()
                            } else {

                                    entradasViewModel.registrarEntradaConInquilino(
                                        nombre.value,
                                        apellido.value,
                                        cedula.value,
                                        telefono.value,
                                        num_apartamento.value,
                                        num_torre.value
                                    )

                                Toast.makeText(context, "EN EFECTO", Toast.LENGTH_SHORT).show()
                                navController.popBackStack()
                            }
                        }
                        if (categoria.value == "Visitante") {


                            if (
                                razon_V.value.isBlank()
                            ) {
                                Toast.makeText(context, "CAMPOS VACIOS VISITANTE", Toast.LENGTH_SHORT).show()
                            } else {

                                entradasViewModel.registrarEntradaConVisitante(
                                    nombre.value,
                                    apellido.value,
                                    cedula.value,
                                    telefono.value,
                                    razon_V.value,
                                    inquilinoSeleccionado.value!!.id
                                )

                                Toast.makeText(context, "EN EFECTO", Toast.LENGTH_SHORT).show()
                                navController.popBackStack()
                            }

                        }
                        if (categoria.value == "Mantenimiento") {
                            if (
                                compania_M.value.isBlank() ||
                                categoria_M.value.isBlank()
                            ) {
                                Toast.makeText(context, "CAMPOS VACIOS 2", Toast.LENGTH_SHORT).show()
                            } else {
                                    entradasViewModel.registrarEntradaConMantenimiento(
                                        nombre.value,
                                        apellido.value,
                                        cedula.value,
                                        telefono.value,
                                        compania_M.value,
                                        categoria_M.value
                                    )

                                Toast.makeText(context, "EN EFECTO", Toast.LENGTH_SHORT).show()
                                navController.popBackStack()
                            }
                        }
                        if (categoria.value == "Obra") {}


                    }

                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrar Entrada")
            }
        }
    }
}

@Composable
fun BuscarInquilino(
    inquilinos: List<InquilinoM>,
    onSeleccionar: (InquilinoM) -> Unit
) {
    var query by remember { mutableStateOf("") }
    val inquilinosFiltrados = inquilinos.filter {
        it.nombre.contains(query, ignoreCase = true)
    }

    Column {
        TextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Buscar inquilino por nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 300.dp)
        ) {
            items(items = inquilinosFiltrados) { inquilino ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSeleccionar(inquilino) }
                        .padding(8.dp)
                ) {
                    Text("Nombre: ${inquilino.nombre}")
                    Text("Cédula: ${inquilino.num_Documento}")
                }
                Divider()
            }
        }
    }
}





