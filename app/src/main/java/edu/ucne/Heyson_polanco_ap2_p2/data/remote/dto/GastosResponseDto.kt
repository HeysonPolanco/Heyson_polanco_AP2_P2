package edu.ucne.Heyson_polanco_ap2_p2.data.remote.dto

import com.squareup.moshi.Json

data class GastosResponseDto(
    @Json(name = "gastoId") val gastoId: Int,
    @Json(name = "fecha") val fecha: String? = null,
    @Json(name = "suplidor") val suplidor: String,
    @Json(name = "ncf") val ncf: String,
    @Json(name = "itbis") val itbis: Double? = null,
    @Json(name = "monto") val monto: Double? = null
)