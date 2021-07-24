package com.example.currencyconverter.ui.currencies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.currencyconverter.data.database.AppDatabaseDao

class CurrencyListViewModelFactory(
    private val dataSource: AppDatabaseDao
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CurrencyListViewModel::class.java)) {
            return CurrencyListViewModel(database = dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}