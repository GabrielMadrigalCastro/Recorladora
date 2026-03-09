package com.example.recorladora.database

import com.example.recorladora.repository.IFormulaRepository

class UpdateFormulaUseCase(private val repository: IFormulaRepository) {
    suspend operator fun invoke(id: Long, title: String, content: String, answer: String) =
        repository.update(id, title, content, answer)
}