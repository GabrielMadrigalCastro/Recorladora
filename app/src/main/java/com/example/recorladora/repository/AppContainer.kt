package com.example.recorladora.repository

import com.example.recorladora.data.FakeFormulaRepository
import com.example.recorladora.database.AddFormulaUseCase
import com.example.recorladora.database.DeleteFormulaUseCase
import com.example.recorladora.database.ObserveAllFormulasUseCase
import com.example.recorladora.database.ObserveFormulaByIdUseCase
import com.example.recorladora.database.UpdateFormulaUseCase

class AppContainer {
    val formulaRepository: IFormulaRepository = FakeFormulaRepository()
    val observeAllFormulas = ObserveAllFormulasUseCase(formulaRepository)
    val observeFormulaById = ObserveFormulaByIdUseCase(formulaRepository)
    val addFormula = AddFormulaUseCase(formulaRepository)
    val updateFormula = UpdateFormulaUseCase(formulaRepository)
    val deleteFormula = DeleteFormulaUseCase(formulaRepository)
}