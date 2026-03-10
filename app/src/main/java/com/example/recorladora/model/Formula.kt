package com.example.recorladora.model

data class Formula(
    val id: Long,
    val formula: String,
    val answer: String,
    val createdAt: Long,
    val updatedAt: Long
)