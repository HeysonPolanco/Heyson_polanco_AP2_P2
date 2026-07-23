package edu.ucne.Heyson_polanco_ap2_p2.domain.model

data class Gastos(
    val gastoId: Int = 0,
    val fecha: String? = null,
    val suplidor: String,
    val ncf: String,
    val itbis: Double? = null,
    val monto: Double? = null
)