package edu.ucne.Heyson_polanco_ap2_p2.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.Heyson_polanco_ap2_p2.data.remote.dto.GastosResponseDto
import edu.ucne.Heyson_polanco_ap2_p2.domain.repository.GastosRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: GastosRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    init {
        savedStateHandle.get<Int>("id")?.let { id ->
            if (id > 0) {
                getGasto(id)
            }
        }
    }

    private fun getGasto(id: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val result = repository.getGastoById(id)
            if (result != null) {
                _uiState.update {
                    it.copy(
                        gastoId = result.gastoId,
                        fecha = result.fecha ?: "",
                        suplidor = result.suplidor,
                        ncf = result.ncf,
                        itbis = result.itbis?.toString() ?: "",
                        monto = result.monto?.toString() ?: "",
                        isLoading = false
                    )
                }
            } else {
                _uiState.update { it.copy(isLoading = false, error = "Gasto no encontrado") }
            }
        }
    }

    fun onFechaChange(fecha: String) = _uiState.update { it.copy(fecha = fecha) }
    fun onSuplidorChange(suplidor: String) = _uiState.update { it.copy(suplidor = suplidor) }
    fun onNcfChange(ncf: String) = _uiState.update { it.copy(ncf = ncf) }
    fun onItbisChange(itbis: String) = _uiState.update { it.copy(itbis = itbis) }
    fun onMontoChange(monto: String) = _uiState.update { it.copy(monto = monto) }

    fun saveGasto() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val dto = GastosResponseDto(
                    gastoId = _uiState.value.gastoId,
                    fecha = _uiState.value.fecha.ifBlank { null },
                    suplidor = _uiState.value.suplidor,
                    ncf = _uiState.value.ncf,
                    itbis = _uiState.value.itbis.toDoubleOrNull(),
                    monto = _uiState.value.monto.toDoubleOrNull()
                )

                if (_uiState.value.gastoId > 0) {
                    repository.updateGasto(dto)
                } else {
                    repository.insertGasto(dto)
                }
                _uiState.update { it.copy(success = true, isLoading = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message ?: "Error al guardar", isLoading = false) }
            }
        }
    }
}