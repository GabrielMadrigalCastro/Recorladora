package com.example.recorladora.presentation.formula.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recorladora.domain.model.Formula
import com.example.recorladora.domain.repository.IFormulaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


data class FormulaListUiState(
    val query: String = "",
    val items: List<Formula> = emptyList(),
    val allItems: List<Formula> = emptyList()
)

class FormulaListViewModel(
    private val repository: IFormulaRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FormulaListUiState())
    val uiState: StateFlow<FormulaListUiState> = _uiState

    init {
        loadFormulas()
    }

    private fun loadFormulas() {
        viewModelScope.launch {
            val list = repository.getAll()
            _uiState.value = _uiState.value.copy(
                items = list,
                allItems = list
            )
        }
    }

    fun onQueryChange(value: String) {
        val current = _uiState.value
        val filtered =
            if (value.isBlank()) current.allItems
            else {
                val q = value.trim().lowercase()
                current.allItems.filter {
                    it.title.lowercase().contains(q) ||
                            it.expression.lowercase().contains(q) ||
                            it.result.lowercase().contains(q)
                }
            }

        _uiState.value = current.copy(
            query = value,
            items = filtered
        )
    }

    fun delete(id: Long) {
        viewModelScope.launch {
            repository.delete(id)

            // 🔥 IMPORTANTE: recargar lista
            loadFormulas()
        }
    }
}