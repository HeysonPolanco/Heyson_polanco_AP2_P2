package edu.ucne.Heyson_polanco_ap2_p2.data.remote

import edu.ucne.Heyson_polanco_ap2_p2.data.remote.dto.GastosDto
import retrofit2.HttpException
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val api: Api
) {
    suspend fun getGastos(): Result<List<GastosDto>> {
        return try {
            val response = api.getGastos()
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error de red ${response.code()}"))
            }
        } catch (e: HttpException) {
            Result.failure(Exception("Error de servidor", e))
        } catch (e: Exception) {
            Result.failure(Exception("Error desconocido ${e.localizedMessage}", e))
        }
    }

    suspend fun getGastoDetail(id: Int): Result<GastosDto> {
        return try {
            val response = api.getGasto(id)

            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error de red ${response.code()}"))
            }
        } catch (e: HttpException) {
            Result.failure(Exception("Error de servidor", e))
        } catch (e: Exception) {
            Result.failure(Exception("Error desconocido", e))
        }
    }

    suspend fun updateGasto(id: Int, gasto: GastosDto): Result<GastosDto> {
        return try {
            val response = api.updateGasto(id, gasto)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error de red ${response.code()}"))
            }
        } catch (e: HttpException) {
            Result.failure(Exception("Error de servidor", e))
        } catch (e: Exception) {
            Result.failure(Exception("Error desconocido", e))
        }
    }
}