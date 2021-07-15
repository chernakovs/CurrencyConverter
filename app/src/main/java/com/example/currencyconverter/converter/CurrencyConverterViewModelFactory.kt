package com.example.currencyconverter.converter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.currencyconverter.currencies.CurrencyListViewModel

class CurrencyConverterViewModelFactory(

) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CurrencyConverterViewModel::class.java)) {
            return CurrencyConverterViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}