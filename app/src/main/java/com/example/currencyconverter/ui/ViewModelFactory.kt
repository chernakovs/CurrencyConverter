package com.example.currencyconverter.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.currencyconverter.di.ServiceLocator
import com.example.currencyconverter.domain.Repository
import com.example.currencyconverter.ui.converter.CurrencyConverterViewModel
import com.example.currencyconverter.ui.currencies.CurrencyListViewModel

class ViewModelFactory(
    private val repository: Repository,
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(CurrencyListViewModel::class.java) ->
                CurrencyListViewModel(
                    repository = repository
                ) as T
            modelClass.isAssignableFrom(CurrencyConverterViewModel::class.java) ->
                CurrencyConverterViewModel(
                    repository = repository,
                    validator = ServiceLocator.provideValueInputValidator()
                ) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}