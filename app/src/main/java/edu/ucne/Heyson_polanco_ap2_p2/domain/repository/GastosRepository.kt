package edu.ucne.Heyson_polanco_ap2_p2.domain.repository

import edu.ucne.Heyson_polanco_ap2_p2.data.remote.Resource
import edu.ucne.Heyson_polanco_ap2_p2.data.remote.dto.GastosResponseDto

interface GastosRepository {
    fun getAllGastos(): Result<List<GastosResponseDto>>
    suspend fun getGastoById(id: Int): GastosResponseDto?
    suspend fun insertGasto(gasto: GastosResponseDto)
    suspend fun deleteGasto(gasto: GastosResponseDto)
    suspend fun updateGasto(gasto: GastosResponseDto)
    suspend fun syncGastos(): Resource<Unit>
}