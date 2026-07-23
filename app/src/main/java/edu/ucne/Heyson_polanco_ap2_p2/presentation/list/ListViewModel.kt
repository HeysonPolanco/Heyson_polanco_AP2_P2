package edu.ucne.Heyson_polanco_ap2_p2.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.Heyson_polanco_ap2_p2.data.remote.Resource
import edu.ucne.Heyson_polanco_ap2_p2.domain.model.Gastos
import edu.ucne.Heyson_polanco_ap2_p2.domain.usecase.GetGastosUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val getGastosUseCase: GetGastosUseCase
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
            getGastosUseCase().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }
                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                gastos = result.data ?: emptyList<Gastos>(),
                                error = ""
                            )
                        }
                    }
                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = result.message ?: "Error al obtener los gastos"
                            )
                        }
                    }
                }
            }
        }
    }
}