package com.example.currencyconverter.ui.converter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.currencyconverter.data.database.AppDatabaseDao
import com.example.currencyconverter.domain.Repository

class CurrencyConverterViewModelFactory(
    private val repository: Repository,
    private val currencyAcronym : String
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CurrencyConverterViewModel::class.java)) {
            return CurrencyConverterViewModel(repository, currencyAcronym) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}