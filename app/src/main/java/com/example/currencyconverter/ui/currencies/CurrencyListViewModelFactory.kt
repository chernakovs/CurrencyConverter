package com.example.currencyconverter.ui.currencies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.currencyconverter.domain.Repository

class CurrencyListViewModelFactory(
    private val repository: Repository
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CurrencyListViewModel::class.java)) {
            return CurrencyListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}