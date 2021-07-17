package com.example.currencyconverter.converter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.currencyconverter.currencies.CurrencyListViewModel
import com.example.currencyconverter.database.AppDatabaseDao

class CurrencyConverterViewModelFactory(
    private val currencyAcronym : String,
    private val dataSource: AppDatabaseDao
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CurrencyConverterViewModel::class.java)) {
            return CurrencyConverterViewModel(dataSource, currencyAcronym) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}