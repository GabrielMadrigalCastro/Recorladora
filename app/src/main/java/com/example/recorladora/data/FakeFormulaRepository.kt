package com.example.recorladora.data

import com.example.recorladora.model.Formula
import com.example.recorladora.repository.IFormulaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class FakeFormulaRepository  : IFormulaRepository {
    private val formulas = MutableStateFlow(
        listOf(
            Formula(1, "Formula 1", "2 + 2", "4", now(), now()),
            Formula(2, "Formula 2", "5 * 3", "15", now(), now()),
            Formula(3, "Formula 3", "10 / 2", "5", now(), now())
        )
    )

    private var nextId: Long = 4L

    override fun observeAll(): Flow<List<Formula>> = formulas

    override fun observeById(id: Long): Flow<Formula?> =
        formulas.map { list -> list.firstOrNull { it.id == id } }

    override suspend fun add(title: String, content: String, answer: String): Long {
        val id = nextId++
        val timestamp = now()

        val newFormula = Formula(
            id,
            title.trim(),
            content.trim(),
            answer.trim(),
            timestamp,
            timestamp
        )

        formulas.update { it + newFormula }

        return id
    }

    override suspend fun update(
        id: Long,
        title: String,
        content: String,
        answer: String
    ): Boolean {

        var updated = false

        formulas.update { list ->
            list.map { note ->
                if (note.id == id) {
                    updated = true
                    note.copy(
                        title = title.trim(),
                        content = content.trim(),
                        answer = answer.trim(),
                        updatedAt = now()
                    )
                } else {
                    note
                }
            }
        }

        return updated
    }

    override suspend fun delete(id: Long): Boolean {
        val before = formulas.value.size

        formulas.update { it.filterNot { note -> note.id == id } }

        return formulas.value.size != before
    }

    private fun now(): Long = System.currentTimeMillis()
}