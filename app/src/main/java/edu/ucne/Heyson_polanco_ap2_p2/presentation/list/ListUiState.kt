package edu.ucne.Heyson_polanco_ap2_p2.presentation.list

import edu.ucne.Heyson_polanco_ap2_p2.domain.model.Gastos

data class ListUiState(
    val isLoading: Boolean = false,
    val gastos: List<Gastos> = emptyList(),
    val error: String = ""
)