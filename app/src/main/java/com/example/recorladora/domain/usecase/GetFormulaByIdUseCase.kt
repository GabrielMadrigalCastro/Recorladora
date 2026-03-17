package com.example.recorladora.domain.usecase

import com.example.recorladora.domain.model.Formula
import com.example.recorladora.domain.repository.IFormulaRepository
import javax.inject.Inject


class GetFormulaByIdUseCase @Inject constructor(private val repository: IFormulaRepository) {
    suspend operator fun invoke(id: Long): Formula? = repository.getById(id)
}