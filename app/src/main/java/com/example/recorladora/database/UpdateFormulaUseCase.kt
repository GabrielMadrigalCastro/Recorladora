package com.example.recorladora.database

import com.example.recorladora.repository.IFormulaRepository

class UpdateFormulaUseCase(private val repository: IFormulaRepository) {

    suspend operator fun invoke(
        id: Long,
        title: String,
        expression: String,
        result: String
    ): Boolean {
        return repository.update(id, title, expression, result)
    }
}