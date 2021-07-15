package com.example.currencyconverter.currencies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CurrencyListViewModelFactory(

) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CurrencyListViewModel::class.java)) {
            return CurrencyListViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}