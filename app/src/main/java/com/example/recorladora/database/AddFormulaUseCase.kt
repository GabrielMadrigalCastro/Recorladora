package com.example.recorladora.database

import com.example.recorladora.repository.IFormulaRepository


class AddFormulaUseCase(private val repository: IFormulaRepository) {
    suspend operator fun invoke(title: String, content: String, answer: String): Long =
        repository.add(title, content, answer)
}