package com.example.currencyconverter.data

import com.squareup.moshi.Json

data class ConversionResponse(
@Json(name="conversion_result") var conversion_result: String
)