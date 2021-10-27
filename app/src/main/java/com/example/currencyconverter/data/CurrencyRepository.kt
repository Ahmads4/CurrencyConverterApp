package com.example.currencyconverter.data

import com.example.currencyconverter.BuildConfig
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class CurrencyRepository @Inject constructor(private val currencyApi: CurrencyApi) {
    suspend fun getConversionRate(baseCurrency: String, toCurrency: String, amount: Double?): ConversionResponse {
       return currencyApi.convert(BuildConfig.API_KEY, baseCurrency, toCurrency, amount)
    }
    suspend fun getCurrencies(): Array<String?> {
        return currencyApi.getCurrencies(BuildConfig.API_KEY).supported_codes.map { it.first()}.toTypedArray()
    }
}