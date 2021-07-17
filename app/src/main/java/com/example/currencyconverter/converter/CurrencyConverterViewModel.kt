package com.example.currencyconverter.converter

import androidx.lifecycle.ViewModel
import com.example.currencyconverter.database.AppDatabase
import com.example.currencyconverter.database.AppDatabaseDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CurrencyConverterViewModel(
    private val database : AppDatabaseDao,
    currencyAcronym : String
) : ViewModel() {

    private val _currency = MutableStateFlow(currencyAcronym)
    val currency : StateFlow<String>
        get() = _currency

}