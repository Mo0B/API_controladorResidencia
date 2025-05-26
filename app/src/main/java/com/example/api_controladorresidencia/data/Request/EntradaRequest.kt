package com.example.api_controladorresidencia.data.Request

data class EntradaRequest(
    val fecha_entrada: String,
    val fecha_salida: String? = null,
    val categoria: String,
    val referencia: Long,
    val vigilante_reg: Long,
    val perVisitante: Long? = null,
    val perInquilino: Long? = null,
    val perMantenimiento: Long? = null,
    val perObra: Long? = null
)
