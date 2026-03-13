package com.example.recorladora.util

fun evaluateExpression(expression: String): String {
    return try {
        val tokens = tokenize(expression)
        val result = evaluate(tokens)
        result.toString()
    } catch (e: Exception) {
        "Error"
    }
}

private fun tokenize(expr: String): List<String> {
    val tokens = mutableListOf<String>()
    var number = ""

    for (c in expr.replace(" ", "")) {
        if (c.isDigit() || c == '.') {
            number += c
        } else {
            if (number.isNotEmpty()) {
                tokens.add(number)
                number = ""
            }
            tokens.add(c.toString())
        }
    }

    if (number.isNotEmpty()) tokens.add(number)

    return tokens
}

private fun evaluate(tokens: List<String>): Double {

    val values = mutableListOf<Double>()
    val ops = mutableListOf<String>()

    fun applyOp() {
        val b = values.removeAt(values.lastIndex)
        val a = values.removeAt(values.lastIndex)
        val op = ops.removeAt(ops.lastIndex)

        val result = when (op) {
            "+" -> a + b
            "-" -> a - b
            "*" -> a * b
            "/" -> a / b
            "%" -> a % b
            else -> 0.0
        }

        values.add(result)
    }

    fun precedence(op: String): Int {
        return when (op) {
            "+", "-" -> 1
            "*", "/", "%" -> 2
            else -> 0
        }
    }

    for (token in tokens) {
        if (token.toDoubleOrNull() != null) {
            values.add(token.toDouble())
        } else {
            while (ops.isNotEmpty() && precedence(ops.last()) >= precedence(token)) {
                applyOp()
            }
            ops.add(token)
        }
    }

    while (ops.isNotEmpty()) {
        applyOp()
    }

    return values.last()
}