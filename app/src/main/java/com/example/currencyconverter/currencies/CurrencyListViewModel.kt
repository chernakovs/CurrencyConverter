package com.example.currencyconverter.currencies

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.database.AppDatabaseDao
import com.example.currencyconverter.network.CurrencyApi
import com.example.currencyconverter.network.NetworkCurrency
import com.example.currencyconverter.network.asNetworkData
import com.example.currencyconverter.repository.CurrencyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CurrencyListViewModel(val database : AppDatabaseDao) : ViewModel() {

    private val repository = CurrencyRepository(database)


    private val _networkError = MutableStateFlow(false)
    val networkError : StateFlow<Boolean> = _networkError

    private val _searchQuery = MutableStateFlow("")
    val searchQuery : StateFlow<String> = _searchQuery


    val currencies = repository.currencies
        .combine(searchQuery) { rates, query -> rates.filter { it.acronym.contains(query, true) || it.title.contains(query, true) } }


    init {
        refreshDataFromRepository()
    }

    private fun refreshDataFromRepository() {
        viewModelScope.launch {
            try {
                repository.refreshCurrencies()
            } catch (networkError: IOException) {
                _networkError.value = true
            }
        }
    }

    fun setSearchQuery(query : String) {
        _searchQuery.value = query
    }

}