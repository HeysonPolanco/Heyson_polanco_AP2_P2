package edu.ucne.Heyson_polanco_ap2_p2.data.remote

import edu.ucne.Heyson_polanco_ap2_p2.data.remote.dto.GastosDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface Api {
    @GET("api/Gastos")
    suspend fun getGastos(): Response<List<GastosDto>>

    @GET("api/Gastos/{id}")
    suspend fun getGasto(@Path("id") id: Int): Response<GastosDto>

    @POST("api/Gastos")
    suspend fun saveGasto(@Body gasto: GastosDto): Response<GastosDto>

    @PUT("api/Gastos/{id}")
    suspend fun updateGasto(
        @Path("id") id: Int,
        @Body gasto: GastosDto
    ): Response<GastosDto>
}