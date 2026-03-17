package com.example.recorladora.domain.usecase

import com.example.recorladora.domain.repository.IFormulaRepository
import javax.inject.Inject


class CreateFormulaUseCase @Inject constructor(private val repository: IFormulaRepository) {
    suspend operator fun invoke(title: String, expression: String, result: String) =
        repository.create(title, expression, result)
}