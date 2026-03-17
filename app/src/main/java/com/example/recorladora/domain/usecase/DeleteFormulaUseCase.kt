package com.example.recorladora.domain.usecase

import com.example.recorladora.domain.repository.IFormulaRepository
import javax.inject.Inject


class DeleteFormulaUseCase @Inject constructor(private val repository: IFormulaRepository) {
    suspend operator fun invoke(id: Long): Boolean = repository.delete(id)
}