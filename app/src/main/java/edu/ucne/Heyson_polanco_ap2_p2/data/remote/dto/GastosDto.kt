package edu.ucne.Heyson_polanco_ap2_p2.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import edu.ucne.Heyson_polanco_ap2_p2.domain.model.Gastos

@JsonClass(generateAdapter = true)
data class GastosDto(
    val gastoId: Int,
    val fecha: String,
    val suplidor: String,
    val ncf: String,
    val itbis: Double,
    val monto: Double
){
    fun toDomain() = Gastos(
        gastoId = gastoId,
        fecha = fecha,
        suplidor = suplidor,
        ncf = ncf,
        itbis = itbis,
        monto = monto
    )
}