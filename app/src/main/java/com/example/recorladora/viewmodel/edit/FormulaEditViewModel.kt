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
    private val observeById: ObserveFormulaByIdUseCase,
    private val add: AddFormulaUseCase,
    private val update: UpdateFormulaUseCase
) : ViewModel() {
    private val title = MutableStateFlow("")
    private val expression = MutableStateFlow("")
    private val result = MutableStateFlow("")
    private val loaded = MutableStateFlow(false)

    val uiState: StateFlow<FormulaEditUiState> =
        combine(title, expression, result, loaded) { t, e, r, isLoaded ->

            val isEdit = id != null

            FormulaEditUiState(
                title = t,
                expression = e,
                result = r,
                isEdit = isEdit
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
                        title.value = item.title
                        expression.value = item.expression
                        result.value = item.result
                        loaded.value = true
                    }
                }
            }
        } else {
            loaded.value = true
        }
    }

    fun onTitleChange(v: String) {
        title.value = v
    }

    fun onExpressionChange(v: String) {
        expression.value = v
        result.value = evaluateExpression(v)
    }

    fun save(onDone: () -> Unit) {
        viewModelScope.launch {
            val t = title.value
            val e = expression.value
            val r = result.value

            if (id == null)
                add(t, e, r)
            else
                update(id, t, e, r)

            onDone()
        }
    }
}