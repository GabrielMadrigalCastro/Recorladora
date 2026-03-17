package com.example.recorladora.presentation.formula.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recorladora.database.DeleteFormulaUseCase
import com.example.recorladora.database.ObserveAllFormulasUseCase
import com.example.recorladora.model.Formula
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


data class FormulaListUiState(
    val query: String = "", val items: List<Formula> = emptyList()
)

class FormulaListViewModel(
    private val observeFormulas: ObserveAllFormulasUseCase, private val deleteFormula: DeleteFormulaUseCase
) : ViewModel() {
    private val query = MutableStateFlow("")
    private val formulas = observeFormulas()

    val uiState: StateFlow<FormulaListUiState> =
        combine(query, formulas) { q, list ->

            val filtered =
                if (q.isBlank()) list
                else {
                    val qq = q.trim().lowercase()

                    list.filter {
                        it.title.lowercase().contains(qq) ||
                                it.expression.lowercase().contains(qq) ||
                                it.result.lowercase().contains(qq)
                    }
                }

            FormulaListUiState(
                query = q,
                items = filtered
            )

        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            FormulaListUiState()
        )

    fun onQueryChange(value: String) {
        query.value = value
    }

    fun delete(id: Long) {
        viewModelScope.launch { deleteFormula(id) }
    }
}