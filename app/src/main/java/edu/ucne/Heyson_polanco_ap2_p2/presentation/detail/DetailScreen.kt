@file:Suppress("SpellCheckingInspection")
package edu.ucne.Heyson_polanco_ap2_p2.presentation.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    id: Int,
    viewModel: DetailViewModel = hiltViewModel(),
    onBack: () -> Unit
){
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(id) {
        viewModel.loadGasto(id)
    }

    LaunchedEffect(state.success) {
        if (state.success) {
            onBack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (id > 0) "Editar Gasto" else "Registro de Gasto") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Atrás"
                        )
                    }
                }
            )
        }
    ) { padding ->
        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (state.error.isNotBlank()) {
                    Text(
                        text = "Error: ${state.error}",
                        color = MaterialTheme.colorScheme.error
                    )
                }

                OutlinedTextField(
                    value = state.suplidor,
                    onValueChange = viewModel::onSuplidorChange,
                    label = { Text("Suplidor") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = state.ncf,
                    onValueChange = viewModel::onNcfChange,
                    label = { Text("NCF") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = state.itbis,
                    onValueChange = viewModel::onItbisChange,
                    label = { Text("ITBIS") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                OutlinedTextField(
                    value = state.monto,
                    onValueChange = viewModel::onMontoChange,
                    label = { Text("Monto") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                OutlinedTextField(
                    value = state.fecha,
                    onValueChange = viewModel::onFechaChange,
                    label = { Text("Fecha (Ej: 2026-07-23)") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = { viewModel.saveGasto() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Guardar")
                }
            }
        }
    }
}