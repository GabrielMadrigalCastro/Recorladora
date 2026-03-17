package com.example.recorladora.data.remote.mapper

import com.example.recorladora.data.remote.dto.FormulaDto
import com.example.recorladora.data.remote.dto.FormulaRequestDto
import com.example.recorladora.domain.model.Formula


fun FormulaDto.toDomain(): Formula {
    return Formula(
        id = id,
        title = title,
        expression = expression,
        result = result,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun Formula.toRequestDto(): FormulaRequestDto {
    return FormulaRequestDto(
        title = title, expression = expression, result = result
    )
}