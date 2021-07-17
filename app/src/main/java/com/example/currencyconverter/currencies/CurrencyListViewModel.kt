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
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CurrencyListViewModel(val database : AppDatabaseDao) : ViewModel() {

    private val repository = CurrencyRepository(database)

    val currencies = repository.currencies

    private val _networkError = MutableStateFlow(false)
    val networkError : StateFlow<Boolean>
        get() = _networkError


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

}