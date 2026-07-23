package edu.ucne.Heyson_polanco_ap2_p2.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.Heyson_polanco_ap2_p2.data.remote.Resource
import edu.ucne.Heyson_polanco_ap2_p2.domain.model.Gastos
import edu.ucne.Heyson_polanco_ap2_p2.domain.repository.GastosRepository
import edu.ucne.Heyson_polanco_ap2_p2.domain.usecase.GetGastosDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getGastosDetailUseCase: GetGastosDetailUseCase,
    private val repository: GastosRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    fun loadGasto(id: Int) {
        if (id <= 0) {
            _uiState.update { it.copy(fecha = "2026-07-23") }
            return
        }
        viewModelScope.launch {
            getGastosDetailUseCase(id).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }
                    is Resource.Success -> {
                        val gasto = result.data
                        if (gasto != null) {
                            _uiState.update {
                                it.copy(
                                    gastoId = gasto.gastoId,
                                    fecha = gasto.fecha ?: "",
                                    suplidor = gasto.suplidor,
                                    ncf = gasto.ncf,
                                    itbis = gasto.itbis?.toString() ?: "",
                                    monto = gasto.monto?.toString() ?: "",
                                    isLoading = false
                                )
                            }
                        }
                    }
                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(isLoading = false, error = result.message ?: "Gasto no encontrado")
                        }
                    }
                }
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
                val gastoToSave = Gastos(
                    gastoId = _uiState.value.gastoId,
                    fecha = _uiState.value.fecha.ifBlank { "" },
                    suplidor = _uiState.value.suplidor,
                    ncf = _uiState.value.ncf,
                    itbis = _uiState.value.itbis.toDoubleOrNull() ?: 0.0,
                    monto = _uiState.value.monto.toDoubleOrNull() ?: 0.0
                )

                if (_uiState.value.gastoId > 0) {
                    repository.updateGasto(gastoToSave)
                } else {
                    repository.insertGasto(gastoToSave)
                }
                _uiState.update { it.copy(success = true, isLoading = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message ?: "Error al guardar", isLoading = false) }
            }
        }
    }
}