package com.example.recorladora.data

import com.example.recorladora.model.Formula
import com.example.recorladora.repository.IFormulaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class FakeFormulaRepository : IFormulaRepository {

    private val formulas = MutableStateFlow(
        listOf(
            Formula(1, "Suma básica", "2 + 2", "4.0", now(), now()),
            Formula(2, "Multiplicación", "5 * 3", "15.0", now(), now()),
            Formula(3, "División", "10 / 2", "5.0", now(), now())
        )
    )

    private var nextId: Long = 4L

    override fun observeAll(): Flow<List<Formula>> = formulas

    override fun observeById(id: Long): Flow<Formula?> =
        formulas.map { list -> list.firstOrNull { it.id == id } }

    override suspend fun add(
        title: String,
        expression: String,
        result: String
    ): Long {

        val id = nextId++
        val timestamp = now()

        val newFormula = Formula(
            id = id,
            title = title.trim(),
            expression = expression.trim(),
            result = result.trim(),
            createdAt = timestamp,
            updatedAt = timestamp
        )

        formulas.update { it + newFormula }

        return id
    }

    override suspend fun update(
        id: Long,
        title: String,
        expression: String,
        result: String
    ): Boolean {

        var updated = false

        formulas.update { list ->
            list.map { item ->
                if (item.id == id) {
                    updated = true
                    item.copy(
                        title = title.trim(),
                        expression = expression.trim(),
                        result = result.trim(),
                        updatedAt = now()
                    )
                } else {
                    item
                }
            }
        }

        return updated
    }

    override suspend fun delete(id: Long): Boolean {

        val before = formulas.value.size

        formulas.update { it.filterNot { item -> item.id == id } }

        return formulas.value.size != before
    }

    private fun now(): Long = System.currentTimeMillis()
}