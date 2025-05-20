package com.example.api_controladorresidencia.data.model

data class EntradaM(
    val id: Long = 0,
    val fecha_entrada: String,
    val fecha_salida: String? = null,
    val categoria: String,
    val referencia: Long,
    val vigilante_reg: VigilanteM,
    val perVisitante: VisitanteM? = null,
    val perInquilino: InquilinoM? = null,
    val perMantenimiento: MantenimientoM? = null,
    val perObra: ObraM? = null
)
