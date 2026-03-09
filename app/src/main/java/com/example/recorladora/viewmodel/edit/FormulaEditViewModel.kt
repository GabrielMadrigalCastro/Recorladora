package com.example.recorladora.viewmodel.edit


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recorladora.database.AddFormulaUseCase
import com.example.recorladora.database.ObserveFormulaByIdUseCase
import com.example.recorladora.database.UpdateFormulaUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


data class FormulaEditUiState(
    val isEdit: Boolean = false,
    val title: String = "",
    val content: String = "",
    val answer: String = "",
    val canSave: Boolean = false
)

class FormulaEditViewModel(
    private val id: Long?,
    private val observeById: ObserveFormulaByIdUseCase,
    private val add: AddFormulaUseCase,
    private val update: UpdateFormulaUseCase
) : ViewModel() {
    private val title = MutableStateFlow("")
    private val content = MutableStateFlow("")
    private val answer = MutableStateFlow("")
    private val loaded = MutableStateFlow(false)

    val uiState: StateFlow<FormulaEditUiState> =
        combine(title, content, answer, loaded) { t, c, a, isLoaded ->

            val isEdit = id != null

            val canSave =
                t.trim().isNotEmpty() &&
                        c.trim().isNotEmpty() &&
                        a.trim().isNotEmpty()

            FormulaEditUiState(
                isEdit = isEdit,
                title = t,
                content = c,
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
                observeById(id).collect { formula ->
                    if (formula != null && !loaded.value) {
                        title.value = formula.title
                        content.value = formula.content
                        answer.value = formula.answer
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

    fun onContentChange(v: String) {
        content.value = v
    }

    fun onAnswerChange(v: String) {
        answer.value = v
    }

    fun save(onDone: () -> Unit) {
        viewModelScope.launch {

            val t = title.value
            val c = content.value
            val a = answer.value

            if (id == null)
                add(t, c, a)
            else
                update(id, t, c, a)

            onDone()
        }
    }
}