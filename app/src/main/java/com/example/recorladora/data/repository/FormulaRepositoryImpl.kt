package com.example.recorladora.data.repository

import com.example.recorladora.data.remote.api.FormulasApi
import com.example.recorladora.data.remote.dto.FormulaRequestDto
import com.example.recorladora.data.remote.mapper.toDomain
import com.example.recorladora.domain.model.Formula
import com.example.recorladora.domain.repository.IFormulaRepository
import javax.inject.Inject


class FormulaRepositoryImpl @Inject constructor(
    private val api: FormulasApi
) : IFormulaRepository {
    override suspend fun getAll(): List<Formula> {
        return api.getFormulas().map { it.toDomain() }
    }

    override suspend fun getById(id: Long): Formula? {
        return try {
            api.getFormulaById(id).toDomain()
        } catch (_: Exception) {
            null
        }
    }

    override suspend fun create(title: String, expression: String, result: String): Formula {
        return api.createFormula(
            FormulaRequestDto(
                title = title, expression = expression, result = result
            )
        ).toDomain()
    }

    override suspend fun update(id: Long, title: String, expression: String, result: String): Formula {
        return api.updateFormula(
            id = id, request = FormulaRequestDto(
                title = title, expression = expression, result = result
            )
        ).toDomain()
    }

    override suspend fun delete(id: Long): Boolean {
        return try {
            api.deleteFormula(id)
            true
        } catch (_: Exception) {
            false
        }
    }
}