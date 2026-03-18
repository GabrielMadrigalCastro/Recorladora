package com.example.recorladora.network

import retrofit2.http.*

interface FormulaApiService {
    @GET("recorladora")
    suspend fun getAllFormulas(): List<FormulaApi>

    @GET("recorladora/{id}")
    suspend fun getFormulaById(@Path("id") id: String): FormulaApi

    @POST("recorladora")
    suspend fun createFormula(@Body formula: FormulaApi): FormulaApi

    @PUT("recorladora/{id}")
    suspend fun updateFormula(@Path("id") id: String, @Body formula: FormulaApi): FormulaApi

    @DELETE("recorladora/{id}")
    suspend fun deleteFormula(@Path("id") id: String): retrofit2.Response<Unit>
}