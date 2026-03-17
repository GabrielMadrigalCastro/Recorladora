package com.example.recorladora.domain.repository

import com.example.recorladora.domain.model.Formula

interface IFormulaRepository {
    suspend fun getAll(): List<Formula>
    suspend fun getById(id: Long): Formula?
    suspend fun create(title: String, expression: String, result: String): Formula
    suspend fun update(id: Long, title: String, expression: String, result: String): Formula
    suspend fun delete(id: Long): Boolean
}