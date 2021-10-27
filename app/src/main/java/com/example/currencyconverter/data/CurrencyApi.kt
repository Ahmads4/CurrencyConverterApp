package com.example.currencyconverter.data

import com.example.currencyconverter.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Path

interface CurrencyApi {
    companion object {
        const val BASE_URL = "https://v6.exchangerate-api.com/v6/"
    }

    @GET("{access_key}/pair/{currency_from}/{currency_to}/{amount}")
    suspend fun convert(
        @Path("access_key") access_key: String = BuildConfig.API_KEY,
        @Path("currency_from") currency_from: String,
        @Path("currency_to") currency_to: String,
        @Path("amount") amount: Double? = 0.0
    ): ConversionResponse

    @GET("{access_key}/codes")
    suspend fun getCurrencies(
        @Path("access_key") access_key: String = BuildConfig.API_KEY
    ): CurrencyResponse


}