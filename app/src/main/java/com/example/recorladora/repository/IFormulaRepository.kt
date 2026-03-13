package com.example.recorladora.repository

import com.example.recorladora.model.Formula
import kotlinx.coroutines.flow.Flow

interface IFormulaRepository {

    fun observeAll(): Flow<List<Formula>>

    fun observeById(id: Long): Flow<Formula?>

    suspend fun add(
        title: String,
        expression: String,
        result: String
    ): Long

    suspend fun update(
        id: Long,
        title: String,
        expression: String,
        result: String
    ): Boolean

    suspend fun delete(id: Long): Boolean
}