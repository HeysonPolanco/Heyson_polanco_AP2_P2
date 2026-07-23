package edu.ucne.Heyson_polanco_ap2_p2.domain.repository

import edu.ucne.Heyson_polanco_ap2_p2.data.remote.Resource
import edu.ucne.Heyson_polanco_ap2_p2.domain.model.Gastos
import kotlinx.coroutines.flow.Flow

interface GastosRepository {
    fun getAllGastos(): Flow<Resource<List<Gastos>>>
    fun getGastoById(id: Int): Flow<Resource<Gastos>>
    suspend fun insertGasto(gasto: Gastos)
    suspend fun deleteGasto(gasto: Gastos)
    suspend fun updateGasto(gasto: Gastos)
    suspend fun syncGastos(): Resource<Unit>
}