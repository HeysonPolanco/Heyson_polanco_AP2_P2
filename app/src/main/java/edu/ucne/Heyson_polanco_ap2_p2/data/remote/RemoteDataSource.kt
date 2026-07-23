package edu.ucne.Heyson_polanco_ap2_p2.data.remote

import edu.ucne.Heyson_polanco_ap2_p2.data.remote.dto.GastosRequestDto
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val api: Api
) {
    suspend fun getGastos() = api.getGastos()

    suspend fun getGasto(id: Int) = api.getGasto(id)

    suspend fun saveGasto(gasto: GastosRequestDto) = api.saveGasto(gasto)

    suspend fun updateGasto(id: Int, gasto: GastosRequestDto) = api.updateGasto(id, gasto)
}