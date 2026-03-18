package com.example.recorladora.data

import com.example.recorladora.model.Formula
import com.example.recorladora.network.FormulaApi
import com.example.recorladora.network.FormulaApiService
import com.example.recorladora.repository.IFormulaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class ApiFormulaRepository(
    private val apiService: FormulaApiService
) : IFormulaRepository {

    override fun observeAll(): Flow<List<Formula>> = flow {
        try {
            val formulas = apiService.getAllFormulas().map { it.toDomain() }
            emit(formulas)
        } catch (e: Exception) {
            // Capturamos Exception en lugar de solo IOException para manejar HttpException (404, etc.)
            emit(emptyList())
        }
    }

    override fun observeById(id: Long): Flow<Formula?> = flow {
        try {
            val formula = apiService.getFormulaById(id.toString()).toDomain()
            emit(formula)
        } catch (e: Exception) {
            // Si no encuentra el ID o hay error, emitimos null
            emit(null)
        }
    }

    override suspend fun add(
        title: String,
        expression: String,
        result: String
    ): Long {
        return try {
            val newFormula = FormulaApi.new(title, expression, result)
            val created = apiService.createFormula(newFormula)
            created.id.toLongOrNull() ?: -1
        } catch (e: Exception) {
            -1 // Error al crear
        }
    }

    override suspend fun update(
        id: Long,
        title: String,
        expression: String,
        result: String
    ): Boolean {
        return try {
            // Primero obtenemos la fórmula existente para mantener createdAt
            val existing = apiService.getFormulaById(id.toString())

            val updatedFormula = existing.copy(
                title = title,
                expression = expression,
                result = result,
                updatedAt = System.currentTimeMillis()
            )

            apiService.updateFormula(id.toString(), updatedFormula)
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun delete(id: Long): Boolean {
        return try {
            val response = apiService.deleteFormula(id.toString())
            response.isSuccessful
        } catch (e: Exception) {
            false
        }
    }
}