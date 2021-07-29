package com.example.currencyconverter.ui.converter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.domain.Repository
import com.example.currencyconverter.ui.di.ServiceLocator
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import okio.IOException

class CurrencyConverterViewModel(
    private val repository: Repository,
    private val currencyAcronym: String
) : ViewModel() {


    private val validator = ServiceLocator.provideValueInputValidator()


    private val _networkError = MutableStateFlow(false)
    val networkError: StateFlow<Boolean> = _networkError

    private val _valueInputError = MutableStateFlow(false)
    val valueInputError: StateFlow<Boolean> = _valueInputError


    private val totalValue = MutableStateFlow(1.0)

    private val _valueString = MutableStateFlow(totalValue.value.toString())
    val valueString: StateFlow<String> = _valueString

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery


    val baseCurrency = repository.getCurrency(currencyAcronym)

    val latestUpdateDate = repository.getBaseCurrencyLatestUpdateDate(currencyAcronym)

    val rates = repository.getRatesByBaseAcronym(currencyAcronym)
        .combine(searchQuery) { rates, query ->
            rates.filter {
                it.currencyAcronym.contains(
                    query,
                    true
                )
            }
        }
        .combine(totalValue) { rates, value -> rates.onEach { it.totalValue = it.cost * value } }


    init {
        refreshDataFromRepository()
    }

    private fun refreshDataFromRepository() {
        viewModelScope.launch {
            try {
                repository.refreshCurrencyRates(currencyAcronym)
            } catch (networkError: IOException) {
                _networkError.value = true
            }
        }
    }

    fun setValue(value: String) {
        if (validator.validate(value)) {
            _valueInputError.value = false
            _valueString.value = value
            totalValue.value = value.toDouble()
        } else {
            _valueInputError.value = true
            _valueString.value = value
        }
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

}