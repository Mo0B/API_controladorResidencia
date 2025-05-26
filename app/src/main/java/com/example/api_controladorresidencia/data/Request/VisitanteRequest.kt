package com.example.api_controladorresidencia.data.Request

import com.example.api_controladorresidencia.data.model.InquilinoM
import com.example.api_controladorresidencia.data.model.VigilanteM

data class VisitanteRequest (
    val nombre: String,
    val apellido: String,
    val telefono: String,
    val num_Documento: String,
    val razon: String,
    val fecha_visita: String,
    val inquilino: Long,
    val vigilante_reg: Long
)