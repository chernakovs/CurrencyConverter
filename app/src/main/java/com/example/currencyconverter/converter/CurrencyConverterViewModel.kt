package com.example.currencyconverter.converter

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.database.AppDatabase
import com.example.currencyconverter.database.AppDatabaseDao
import com.example.currencyconverter.network.CurrencyApi
import com.example.currencyconverter.repository.CurrencyRateRepository
import com.example.currencyconverter.repository.CurrencyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import okio.IOException

class CurrencyConverterViewModel(
    private val database : AppDatabaseDao,
    currencyAcronym : String
) : ViewModel() {

    private val repository = CurrencyRateRepository(database, currencyAcronym)

    private val validator = ValueInputValidator()



    private val _networkError = MutableStateFlow(false)
    val networkError : StateFlow<Boolean>
        get() = _networkError

    private val _valueInputError = MutableStateFlow(false)
    val valueInputError : StateFlow<Boolean>
        get() = _valueInputError



    private val totalValue = MutableStateFlow(1.0)

    private val _valueString = MutableStateFlow(totalValue.toString())
    val valueString : StateFlow<String>
        get() = _valueString

    val rates = repository.rates
        .combine(totalValue) { rates, value -> rates.onEach { it.totalValue = it.cost * value } }

    init {
        refreshDataFromRepository()
    }

    private fun refreshDataFromRepository() {
        viewModelScope.launch {
            try {
                repository.refreshCurrencyRates()
            } catch (networkError: IOException) {
                _networkError.value = true
            }
        }
    }

    fun setValue(value : String) {
        if (validator.validate(value)) {
            _valueInputError.value = false
            _valueString.value = value
            totalValue.value = value.toDouble()
        }
        else {
            _valueInputError.value = true
            _valueString.value = value
        }
    }

}