package edu.ucne.Heyson_polanco_ap2_p2.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.Heyson_polanco_ap2_p2.domain.repository.GastosRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val repository: GastosRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ListUiState())
    val uiState: StateFlow<ListUiState> = _uiState.asStateFlow()

    init {
        getGastos()
    }

    fun onEvent(event: ListEvent) {
        when (event) {
            is ListEvent.OnRefresh -> getGastos()
            else -> {}
        }
    }

    private fun getGastos() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val result = repository.getAllGastos()

            result.onSuccess { gastosList ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        gastos = gastosList ?: emptyList(),
                        error = ""
                    )
                }
            }.onFailure { exception ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = exception.message ?: "Error al obtener los gastos"
                    )
                }
            }
        }
    }
}