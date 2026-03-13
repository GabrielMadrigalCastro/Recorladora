package com.example.recorladora.viewmodel.edit

import androidx.compose.ui.res.stringResource
import com.example.recorladora.R
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.recorladora.repository.AppContainer


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormulaEditScreen(
    container: AppContainer, id: Long?, onDone: () -> Unit
) {
    val viewModel = remember(id) {
        FormulaEditViewModel(
            id = id,
            observeById = container.observeFormulaById,
            add = container.addFormula,
            update = container.updateFormula
        )
    }
    val state by viewModel.uiState.collectAsState()


    Scaffold(
        topBar = {
            TopAppBar(title = { Text(if (state.isEdit) stringResource(R.string.edit) + " " + stringResource(R.string.function) else stringResource(R.string.new_formula) )})
        }) { pad ->
        Column(
            Modifier
                .padding(pad)
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = state.title,
                onValueChange = viewModel::onTitleChange,
                label = { Text(stringResource(R.string.expression)) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = state.expression,
                onValueChange = viewModel::onExpressionChange,
                label = { Text(stringResource(R.string.function)) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = if (state.result == "Error")
                    stringResource(R.string.error)
                else
                    "${stringResource(R.string.result)}: ${state.result}"
            )

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = { viewModel.save(onDone) },
                enabled = state.canSave
            ) {
                Text(stringResource(R.string.save))
            }
        }
    }
}

