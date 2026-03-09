package com.example.recorladora.database

import com.example.recorladora.repository.IFormulaRepository

class DeleteFormulaUseCase(private val repository: IFormulaRepository) {
    suspend operator fun invoke(id: Long): Boolean = repository.delete(id)
}