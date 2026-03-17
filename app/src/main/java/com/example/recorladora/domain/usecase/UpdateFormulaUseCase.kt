package com.example.recorladora.domain.usecase

import com.example.recorladora.domain.model.Formula
import com.example.recorladora.domain.repository.IFormulaRepository
import javax.inject.Inject


class UpdateFormulaUseCase @Inject constructor(private val repository: IFormulaRepository) {
    suspend operator fun invoke(id: Long, title: String, expression: String, result: String): Formula =
        repository.update(id, title, expression, result)
}