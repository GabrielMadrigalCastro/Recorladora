package com.example.recorladora.presentation.formula.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.recorladora.R
import com.example.recorladora.domain.repository.IFormulaRepository
import com.example.recorladora.repository.AppContainer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormulaDetailScreen(
    repository: IFormulaRepository, id: Long, onBack: () -> Unit, onEdit: () -> Unit
) {
    val viewModel = remember(id) { FormulaDetailViewModel(id, repository) }
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(stringResource(R.string.detail)) }, navigationIcon = {
                TextButton(onClick = onBack) { Text(stringResource(R.string.back)) }
            }, actions = {
                TextButton(onClick = onEdit) { Text(stringResource(R.string.edit)) }
            })
        }) { pad ->
        val formula = state.formula
        Column(
            Modifier
                .padding(pad)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            if (formula == null) {
                Text((stringResource(R.string.the_formula_doesnt_exist)))
            } else {
                Text(
                    formula.title,
                    style = MaterialTheme.typography.headlineSmall
                )

                Spacer(Modifier.height(12.dp))

                Text(
                    stringResource(R.string.expression) + ":",
                    style = MaterialTheme.typography.labelMedium
                )

                Text(formula.expression)

                Spacer(Modifier.height(8.dp))

                Text(
                    stringResource(R.string.result) + ": ${formula.result}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}