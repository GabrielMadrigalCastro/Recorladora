package com.example.recorladora.model

data class Formula(
    val id: Long,
    val title: String,      // nombre que ve el usuario
    val expression: String, // formula matemática
    val result: String,     // resultado
    val createdAt: Long,
    val updatedAt: Long
)