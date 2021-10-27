package com.example.currencyconverter.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.data.CurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CurrencyViewModel @Inject constructor(private val repository: CurrencyRepository): ViewModel() {

    private val _conversionResult = MutableLiveData<String>()
    val conversionResult: LiveData<String> = _conversionResult
    private val _currencies = MutableLiveData<Array<String?>>()
    val currencies: LiveData<Array<String?>> = _currencies

    init {
        getCurrencies()
    }

fun getConversionRate(baseCurrency: String, toCurrency: String, amount: Double?)  {
    viewModelScope.launch {
        _conversionResult.value = repository.getConversionRate(baseCurrency, toCurrency, amount).conversion_result
    }
}
    fun getCurrencies() {
        viewModelScope.launch {
            _currencies.value = repository.getCurrencies()
        }
    }


}