package com.example.currencyconverter.converter

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.database.AppDatabaseDao
import com.example.currencyconverter.repository.CurrencyRateRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import okio.IOException

class CurrencyConverterViewModel(
    private val database : AppDatabaseDao,
    currencyAcronym : String
) : ViewModel() {


    private val repository = CurrencyRateRepository(database, currencyAcronym)

    private val validator = ValueInputValidator()


    private val _networkError = MutableStateFlow(false)
    val networkError : StateFlow<Boolean> = _networkError

    private val _valueInputError = MutableStateFlow(false)
    val valueInputError : StateFlow<Boolean> = _valueInputError


    private val totalValue = MutableStateFlow(1.0)

    private val _valueString = MutableStateFlow(totalValue.value.toString())
    val valueString : StateFlow<String> = _valueString

    private val _searchQuery = MutableStateFlow("")
    val searchQuery : StateFlow<String> = _searchQuery


    val baseCurrency = repository.baseCurrency

    val latestUpdateDate = repository.latestDate

    val rates = repository.rates
        .combine(searchQuery) { rates, query -> rates.filter { it.currencyAcronym.contains(query, true) } }
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

    fun setSearchQuery(query : String) {
        _searchQuery.value = query
    }

}