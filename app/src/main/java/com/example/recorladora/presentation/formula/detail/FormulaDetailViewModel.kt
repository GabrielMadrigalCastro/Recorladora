package com.example.recorladora.presentation.formula.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recorladora.database.ObserveFormulaByIdUseCase
import com.example.recorladora.domain.repository.IFormulaRepository
import com.example.recorladora.model.Formula
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class FormulaDetailUiState(val formula: Formula? = null)

class FormulaDetailViewModel(
    private val id: Long,
    private val repository: IFormulaRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FormulaDetailUiState())
    val uiState: StateFlow<FormulaDetailUiState> = _uiState

    init {
        loadFormula()
    }

    private fun loadFormula() {
        viewModelScope.launch {
            val formula = repository.getById(id)
            _uiState.value = FormulaDetailUiState(formula)
        }
    }
}