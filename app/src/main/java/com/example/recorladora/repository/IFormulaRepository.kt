package com.example.recorladora.repository

import com.example.recorladora.model.Formula
import kotlinx.coroutines.flow.Flow

interface IFormulaRepository {
    fun observeAll(): Flow<List<Formula>>
    fun observeById(id: Long): Flow<Formula?>
    suspend fun add(formula: String, answer: String): Long

    suspend fun update(
        id: Long,
        formula: String,
        answer: String
    ): Boolean
    suspend fun delete(id: Long): Boolean
}