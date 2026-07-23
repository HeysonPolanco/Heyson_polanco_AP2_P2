package edu.ucne.Heyson_polanco_ap2_p2.domain.usecase

import edu.ucne.Heyson_polanco_ap2_p2.data.remote.Resource
import edu.ucne.Heyson_polanco_ap2_p2.domain.model.Gastos
import edu.ucne.Heyson_polanco_ap2_p2.domain.repository.GastosRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGastosUseCase @Inject constructor(
    private val repository: GastosRepository
) {
    operator fun invoke(): Flow<Resource<List<Gastos>>> {
        return repository.getAllGastos()
    }
}