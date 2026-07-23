package edu.ucne.Heyson_polanco_ap2_p2.data.remote

import edu.ucne.Heyson_polanco_ap2_p2.data.remote.dto.GastosRequestDto
import edu.ucne.Heyson_polanco_ap2_p2.data.remote.dto.GastosResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface Api {
    @GET("api/Gastos")
    suspend fun getGastos(): Response<List<GastosResponseDto>>

    @GET("api/Gastos/{id}")
    suspend fun getGasto(@Path("id") id: Int): Response<GastosResponseDto>

    @POST("api/Gastos")
    suspend fun saveGasto(@Body gasto: GastosRequestDto): Response<GastosResponseDto>

    @PUT("api/Gastos/{id}")
    suspend fun updateGasto(
        @Path("id") id: Int,
        @Body gasto: GastosRequestDto
    ): Response<Unit>
}