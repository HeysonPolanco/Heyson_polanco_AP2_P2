package edu.ucne.Heyson_polanco_ap2_p2.data

import edu.ucne.Heyson_polanco_ap2_p2.data.remote.Api
import edu.ucne.Heyson_polanco_ap2_p2.data.remote.Resource
import edu.ucne.Heyson_polanco_ap2_p2.data.remote.dto.GastosRequestDto
import edu.ucne.Heyson_polanco_ap2_p2.data.remote.dto.GastosResponseDto
import edu.ucne.Heyson_polanco_ap2_p2.domain.repository.GastosRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RepositoryImp @Inject constructor(
    private val api: Api
) : GastosRepository {

    private suspend fun <T> call(block: suspend () -> retrofit2.Response<T>): Result<T?> = try {
        val r = block()
        if (r.isSuccessful) Result.success(r.body()) else Result.failure(IllegalStateException("HTTP ${r.code()}"))
    } catch (e: Exception) {
        Result.failure(e)
    }

    override fun getAllGastos(): Result<List<GastosResponseDto>> = runBlocking {
        withContext(Dispatchers.IO) {
            call { api.getGastos() }.map { it ?: emptyList() }
        }
    }

    override suspend fun getGastoById(id: Int): GastosResponseDto? =
        call { api.getGasto(id) }.getOrNull()

    override suspend fun insertGasto(gasto: GastosResponseDto) {
        val req = GastosRequestDto(
            fecha = gasto.fecha,
            suplidor = gasto.suplidor,
            ncf = gasto.ncf,
            itbis = gasto.itbis ?: 0.0,
            monto = gasto.monto ?: 0.0
        )
        call { api.saveGasto(req) }.getOrThrow()
    }

    override suspend fun deleteGasto(gasto: GastosResponseDto) {
        throw UnsupportedOperationException("DELETE no disponible en la API")
    }

    override suspend fun updateGasto(gasto: GastosResponseDto) {
        val id = requireNotNull(gasto.gastoId)
        val req = GastosRequestDto(
            fecha = gasto.fecha,
            suplidor = gasto.suplidor,
            ncf = gasto.ncf,
            itbis = gasto.itbis ?: 0.0,
            monto = gasto.monto ?: 0.0
        )
        call { api.updateGasto(id, req) }.getOrThrow()
    }

    override suspend fun syncGastos(): Resource<Unit> = Resource.Success(Unit)
}