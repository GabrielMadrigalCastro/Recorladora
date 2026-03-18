package com.example.recorladora.repository

import com.example.recorladora.data.ApiFormulaRepository
import com.example.recorladora.data.FakeFormulaRepository
import com.example.recorladora.database.AddFormulaUseCase
import com.example.recorladora.database.DeleteFormulaUseCase
import com.example.recorladora.database.ObserveAllFormulasUseCase
import com.example.recorladora.database.ObserveFormulaByIdUseCase
import com.example.recorladora.database.UpdateFormulaUseCase
import com.example.recorladora.network.NetworkModule

class AppContainer {
    // Usamos el repositorio API en lugar del Fake
    val formulaRepository: IFormulaRepository = ApiFormulaRepository(
        NetworkModule.formulaApiService
    )
    val observeAllFormulas = ObserveAllFormulasUseCase(formulaRepository)
    val observeFormulaById = ObserveFormulaByIdUseCase(formulaRepository)
    val addFormula = AddFormulaUseCase(formulaRepository)
    val updateFormula = UpdateFormulaUseCase(formulaRepository)
    val deleteFormula = DeleteFormulaUseCase(formulaRepository)
}