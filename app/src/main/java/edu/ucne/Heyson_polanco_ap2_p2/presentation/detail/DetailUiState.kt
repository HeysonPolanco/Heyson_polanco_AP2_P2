package edu.ucne.Heyson_polanco_ap2_p2.presentation.detail

data class DetailUiState(
    val gastoId: Int = 0,
    val fecha: String = "",
    val suplidor: String = "",
    val ncf: String = "",
    val itbis: String = "",
    val monto: String = "",
    val isLoading: Boolean = false,
    val error: String = "",
    val success: Boolean = false
)