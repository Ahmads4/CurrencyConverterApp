package com.example.currencyconverter.data

import com.squareup.moshi.Json


data class CurrencyResponse(
    @Json(name="documentation") val documentation: String,
    @Json(name="result")  val result: String,
    @Json(name="supported_codes") val supported_codes: List<List<String>>,
    @Json(name="terms_of_use") val terms_of_use: String
)