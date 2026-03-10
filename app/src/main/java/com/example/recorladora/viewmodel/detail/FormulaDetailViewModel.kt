package com.example.recorladora.viewmodel.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recorladora.database.ObserveFormulaByIdUseCase
import com.example.recorladora.model.Formula
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class FormulaDetailUiState(val formula: Formula? = null)

class FormulaDetailViewModel(
    id: Long,
    observeById: ObserveFormulaByIdUseCase
) : ViewModel() {

    val uiState: StateFlow<FormulaDetailUiState> =
        observeById(id)
            .map { FormulaDetailUiState(it) }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                FormulaDetailUiState()
            )
}