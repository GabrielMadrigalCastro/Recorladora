package com.example.recorladora.presentation.formula.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recorladora.domain.model.Formula
import com.example.recorladora.domain.repository.IFormulaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class FormulaDetailUiState(
    val formula: Formula? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class FormulaDetailViewModel @Inject constructor(
    private val repository: IFormulaRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val id: Long = checkNotNull(savedStateHandle["id"])

    private val _uiState = MutableStateFlow(FormulaDetailUiState())
    val uiState: StateFlow<FormulaDetailUiState> = _uiState

    init {
        load()
    }

    private fun load() {
        viewModelScope.launch {
            _uiState.value = FormulaDetailUiState(isLoading = true)

            try {
                val formula = repository.getById(id)
                _uiState.value = FormulaDetailUiState(formula = formula)
            } catch (e: Exception) {
                _uiState.value = FormulaDetailUiState(
                    error = e.message ?: "Error desconocido"
                )
            }
        }
    }
}