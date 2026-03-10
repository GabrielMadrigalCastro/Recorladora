package com.example.recorladora.database

import com.example.recorladora.repository.IFormulaRepository



class AddFormulaUseCase(private val repository: IFormulaRepository) {

    suspend operator fun invoke(
        formula: String,
        answer: String
    ): Long {
        return repository.add(formula, answer)
    }
}