package com.example.tuapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.api_controladorresidencia.data.model.InquilinoM
import androidx.compose.foundation.background

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apartment
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Phone

import androidx.compose.ui.Alignment

import androidx.compose.ui.graphics.Color


@Composable
fun InquilinoItem(inquilino: InquilinoM) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2E7D32)), // verde oscuro
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(16.dp)
        ) {
            Text(
                text = "${inquilino.nombre} ${inquilino.apellido}",
                style = MaterialTheme.typography.titleLarge,
                color = Color(0xFF2E7D32)
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Badge, contentDescription = "Documento", tint = Color(0xFF2E7D32))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = inquilino.num_Documento, style = MaterialTheme.typography.bodyMedium, color = Color(0xFF2E7D32))
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Phone, contentDescription = "Teléfono", tint = Color(0xFF2E7D32))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = inquilino.telefono, style = MaterialTheme.typography.bodyMedium, color = Color(0xFF2E7D32))
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Apartment, contentDescription = "Apartamento", tint = Color(0xFF2E7D32))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Torre ${inquilino.num_Torre}, Apto ${inquilino.num_Apartamento}", style = MaterialTheme.typography.bodyMedium, color = Color(0xFF2E7D32))
            }
        }
    }
}
