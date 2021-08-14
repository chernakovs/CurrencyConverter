package com.example.currencyconverter.ui.converter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.domain.Repository
import com.example.currencyconverter.domain.model.Currency
import com.example.currencyconverter.domain.model.CurrencyRates
import com.example.currencyconverter.ui.converter.utils.ValueInputValidator
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import okio.IOException

class CurrencyConverterViewModel(
    private val repository: Repository,
    private val validator: ValueInputValidator,
) : ViewModel() {

    lateinit var baseCurrency: Flow<Currency>

    lateinit var latestUpdateDate: Flow<String>

    lateinit var rates: Flow<List<CurrencyRates>>

    private val _networkError = MutableStateFlow(false)
    val networkError: StateFlow<Boolean> = _networkError

    private val _valueInputError = MutableStateFlow(false)
    val valueInputError: StateFlow<Boolean> = _valueInputError


    private val totalValue = MutableStateFlow(1.0)

    private val _valueString = MutableStateFlow(totalValue.value.toString())
    val valueString: StateFlow<String> = _valueString

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery


    private fun refreshDataFromRepository(acronym: String) {
        viewModelScope.launch {
            try {
                repository.refreshCurrencyRates(acronym)
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

    fun setBaseCurrencyAndRates(acronym: String) {

        baseCurrency = repository.getCurrency(acronym)

        latestUpdateDate = repository.getBaseCurrencyLatestUpdateDate(acronym)

        rates = repository.getRatesByBaseAcronym(acronym)
            .combine(searchQuery) { rates, query ->
                rates.filter {
                    it.currencyAcronym.contains(
                        query,
                        true
                    )
                }
            }
            .combine(totalValue) { rates, value -> rates.onEach { it.totalValue = it.cost * value } }

        refreshDataFromRepository(acronym)
    }

}