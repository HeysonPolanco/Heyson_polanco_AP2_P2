package edu.ucne.Heyson_polanco_ap2_p2.presentation.list

import edu.ucne.Heyson_polanco_ap2_p2.data.remote.dto.GastosResponseDto

data class ListUiState(
    val isLoading: Boolean = false,
    val gastos: List<GastosResponseDto> = emptyList(),
    val error: String = ""
)