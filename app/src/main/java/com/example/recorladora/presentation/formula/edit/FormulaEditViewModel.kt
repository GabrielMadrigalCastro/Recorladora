package com.example.recorladora.presentation.formula.edit


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recorladora.domain.repository.IFormulaRepository
import com.example.recorladora.util.evaluateExpression
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


data class FormulaEditUiState(
    val title: String = "",
    val expression: String = "",
    val result: String = "",
    val isEdit: Boolean = false
) {
    val canSave: Boolean
        get() = title.isNotBlank() && expression.isNotBlank() && result != "Error"
}

class FormulaEditViewModel(
    private val id: Long?,
    private val repository: IFormulaRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        FormulaEditUiState(isEdit = id != null)
    )
    val uiState: StateFlow<FormulaEditUiState> = _uiState

    init {
        if (id != null) {
            loadFormula()
        }
    }

    private fun loadFormula() {
        viewModelScope.launch {
            val formula = repository.getById(id!!)
            if (formula != null) {
                _uiState.value = FormulaEditUiState(
                    title = formula.title,
                    expression = formula.expression,
                    result = formula.result,
                    isEdit = true
                )
            }
        }
    }

    fun onTitleChange(v: String) {
        _uiState.value = _uiState.value.copy(title = v)
    }

    fun onExpressionChange(v: String) {
        val result = evaluateExpression(v)
        _uiState.value = _uiState.value.copy(
            expression = v,
            result = result
        )
    }

    fun save(onDone: () -> Unit) {
        viewModelScope.launch {
            val state = _uiState.value

            if (id == null) {
                repository.create(
                    state.title,
                    state.expression,
                    state.result
                )
            } else {
                repository.update(
                    id,
                    state.title,
                    state.expression,
                    state.result
                )
            }

            onDone()
        }
    }
}