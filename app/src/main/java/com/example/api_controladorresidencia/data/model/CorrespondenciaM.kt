package com.example.api_controladorresidencia.data.model

    data class CorrespondenciaM(
        val id: Long = 0,
        val fecha_entrada: String,
        val categoria: String,
        val empresa: String,
        val inquilino: InquilinoM
    )