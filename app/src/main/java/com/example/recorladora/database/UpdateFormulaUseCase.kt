package com.example.recorladora.database

import com.example.recorladora.repository.IFormulaRepository

class UpdateFormulaUseCase(private val repository: IFormulaRepository) {

    suspend operator fun invoke(
        id: Long,
        formula: String,
        answer: String
    ): Boolean {
        return repository.update(id, formula, answer)
    }
}