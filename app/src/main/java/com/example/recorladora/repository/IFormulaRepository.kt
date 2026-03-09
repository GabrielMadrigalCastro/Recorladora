package com.example.recorladora.repository

import com.example.recorladora.model.Formula
import kotlinx.coroutines.flow.Flow

interface IFormulaRepository {
    fun observeAll(): Flow<List<Formula>>
    fun observeById(id: Long): Flow<Formula?>
    suspend fun add(title: String, content: String, answer: String): Long
    suspend fun update(id: Long, title: String, content: String, answer: String): Boolean
    suspend fun delete(id: Long): Boolean
}