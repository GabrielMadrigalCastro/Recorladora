package com.example.recorladora.repository

import com.example.recorladora.data.FakeFormulaRepository
import com.example.recorladora.database.AddFormulaUseCase
import com.example.recorladora.database.DeleteFormulaUseCase
import com.example.recorladora.database.ObserveAllFormulasUseCase
import com.example.recorladora.database.ObserveFormulaByIdUseCase
import com.example.recorladora.database.UpdateFormulaUseCase

class AppContainer {
    val FormulaRepository: IFormulaRepository = FakeFormulaRepository()
    val observeAllFormulas = ObserveAllFormulasUseCase(FormulaRepository)
    val observeFormulaById = ObserveFormulaByIdUseCase(FormulaRepository)
    val addFormula = AddFormulaUseCase(FormulaRepository)
    val updateFormula = UpdateFormulaUseCase(FormulaRepository)
    val deleteFormula = DeleteFormulaUseCase(FormulaRepository)
}