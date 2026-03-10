package com.example.recorladora.viewmodel.edit


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recorladora.database.AddFormulaUseCase
import com.example.recorladora.database.ObserveFormulaByIdUseCase
import com.example.recorladora.database.UpdateFormulaUseCase
import com.example.recorladora.util.evaluateExpression
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


data class FormulaEditUiState(
    val isEdit: Boolean = false,
    val formula: String = "",
    val answer: String = "",
    val canSave: Boolean = false
)

class FormulaEditViewModel(
    private val id: Long?,
    private val observeById: ObserveFormulaByIdUseCase,
    private val add: AddFormulaUseCase,
    private val update: UpdateFormulaUseCase
) : ViewModel() {
    private val formula = MutableStateFlow("")
    private val answer = MutableStateFlow("")
    private val loaded = MutableStateFlow(false)

    val uiState: StateFlow<FormulaEditUiState> =
        combine(formula, answer, loaded) { f, a, isLoaded ->

            val isEdit = id != null

            val canSave =
                f.trim().isNotEmpty()

            FormulaEditUiState(
                isEdit = isEdit,
                formula = f,
                answer = a,
                canSave = canSave && (isEdit.not() || isLoaded)
            )
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            FormulaEditUiState()
        )

    init {
        if (id != null) {
            viewModelScope.launch {
                observeById(id).collect { item ->
                    if (item != null && !loaded.value) {
                        formula.value = item.formula
                        answer.value = item.answer
                        loaded.value = true
                    }
                }
            }
        } else {
            loaded.value = true
        }
    }

    fun onFormulaChange(v: String) {
        formula.value = v
        answer.value = evaluateExpression(v)
    }

    fun onAnswerChange(v: String) {
        answer.value = v
    }

    fun save(onDone: () -> Unit) {
        viewModelScope.launch {
            val f = formula.value
            val a = answer.value

            if (id == null)
                add(f, a)
            else
                update(id, f, a)

            onDone()
        }
    }
}