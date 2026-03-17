package com.example.recorladora.data.remote.api

import com.example.recorladora.data.remote.dto.FormulaDto
import com.example.recorladora.data.remote.dto.FormulaRequestDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface FormulasApi {
    @GET("formulas")
    suspend fun getFormulas(): List<FormulaDto>

    @GET("formulas/{id}")
    suspend fun getFormulaById(
        @Path("id") id: Long
    ): FormulaDto

    @POST("formulas")
    suspend fun createFormula(
        @Body request: FormulaRequestDto
    ): FormulaDto

    @PUT("formulas/{id}")
    suspend fun updateFormula(
        @Path("id") id: Long, @Body request: FormulaRequestDto
    ): FormulaDto

    @DELETE("formulas/{id}")
    suspend fun deleteFormula(
        @Path("id") id: Long
    )
}