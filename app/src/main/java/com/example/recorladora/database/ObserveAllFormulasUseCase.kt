package com.example.recorladora.database

import com.example.recorladora.model.Formula
import com.example.recorladora.repository.IFormulaRepository
import kotlinx.coroutines.flow.Flow

class ObserveAllFormulasUseCase(private val repository: IFormulaRepository) {
    operator fun invoke(): Flow<List<Formula>> = repository.observeAll()
}