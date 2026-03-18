package com.example.recorladora.network

import com.example.recorladora.model.Formula
import kotlinx.serialization.Serializable

@Serializable
data class FormulaApi(
    val id: String,  // Mockapi.io usa String IDs
    val title: String,
    val expression: String,
    val result: String,
    val createdAt: Long,
    val updatedAt: Long
) {
    // Convertir de Api a modelo de dominio
    fun toDomain(): Formula = Formula(
        id = id.toLongOrNull() ?: 0,
        title = title,
        expression = expression,
        result = result,
        createdAt = createdAt,
        updatedAt = updatedAt
    )

    companion object {
        // Convertir de modelo de dominio a Api (para crear/actualizar)
        fun fromDomain(formula: Formula): FormulaApi = FormulaApi(
            id = formula.id.toString(),
            title = formula.title,
            expression = formula.expression,
            result = formula.result,
            createdAt = formula.createdAt,
            updatedAt = System.currentTimeMillis()
        )

        // Crear una nueva para POST (sin ID)
        fun new(
            title: String,
            expression: String,
            result: String
        ): FormulaApi = FormulaApi(
            id = "", // El servidor asignará el ID
            title = title,
            expression = expression,
            result = result,
            createdAt = System.currentTimeMillis(),
            updatedAt = System.currentTimeMillis()
        )
    }
}