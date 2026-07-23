package edu.ucne.Heyson_polanco_ap2_p2.data

import edu.ucne.Heyson_polanco_ap2_p2.data.remote.Api
import edu.ucne.Heyson_polanco_ap2_p2.data.remote.Resource
import edu.ucne.Heyson_polanco_ap2_p2.data.remote.dto.GastosDto
import edu.ucne.Heyson_polanco_ap2_p2.domain.model.Gastos
import edu.ucne.Heyson_polanco_ap2_p2.domain.repository.GastosRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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

    override fun getAllGastos(): Flow<Resource<List<Gastos>>> = flow {
        emit(Resource.Loading())
        val result = withContext(Dispatchers.IO) {
            call { api.getGastos() }
        }
        result.onSuccess { list ->
            emit(Resource.Success(list?.map { it.toDomain() } ?: emptyList()))
        }.onFailure {
            emit(Resource.Error(it.message ?: "Error desconocido"))
        }
    }

    override fun getGastoById(id: Int): Flow<Resource<Gastos>> = flow {
        emit(Resource.Loading())
        val result = withContext(Dispatchers.IO) {
            call { api.getGasto(id) }
        }
        result.onSuccess { dto ->
            dto?.let { emit(Resource.Success(it.toDomain())) }
        }.onFailure {
            emit(Resource.Error(it.message ?: "Error desconocido"))
        }
    }

    override suspend fun insertGasto(gasto: Gastos) {
        val req = GastosDto(
            gastoId = gasto.gastoId,
            fecha = gasto.fecha ?: "",
            suplidor = gasto.suplidor ?: "",
            ncf = gasto.ncf ?: "",
            itbis = gasto.itbis ?: 0.0,
            monto = gasto.monto ?: 0.0
        )
        call { api.saveGasto(req) }.getOrThrow()
    }

    override suspend fun deleteGasto(gasto: Gastos) {
        throw UnsupportedOperationException("DELETE no disponible en la API")
    }

    override suspend fun updateGasto(gasto: Gastos) {
        val req = GastosDto(
            gastoId = gasto.gastoId,
            fecha = gasto.fecha ?: "",
            suplidor = gasto.suplidor ?: "",
            ncf = gasto.ncf ?: "",
            itbis = gasto.itbis ?: 0.0,
            monto = gasto.monto ?: 0.0
        )
        call { api.updateGasto(gasto.gastoId, req) }.getOrThrow()
    }

    override suspend fun syncGastos(): Resource<Unit> = Resource.Success(Unit)
}