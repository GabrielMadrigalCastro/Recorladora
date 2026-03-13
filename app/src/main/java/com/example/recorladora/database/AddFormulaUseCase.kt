package com.example.recorladora.database

import com.example.recorladora.repository.IFormulaRepository



class AddFormulaUseCase(private val repository: IFormulaRepository) {

    suspend operator fun invoke(
        title: String,
        expression: String,
        result: String
    ): Long {
        return repository.add(title, expression, result)
    }
}