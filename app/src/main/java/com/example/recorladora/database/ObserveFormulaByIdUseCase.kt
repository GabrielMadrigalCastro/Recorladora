package com.example.recorladora.database

import com.example.recorladora.model.Formula
import com.example.recorladora.repository.IFormulaRepository
import kotlinx.coroutines.flow.Flow

class ObserveFormulaByIdUseCase(private val repository: IFormulaRepository) {
    operator fun invoke(id: Long): Flow<Formula?> = repository.observeById(id)
}