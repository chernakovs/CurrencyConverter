package com.example.currencyconverter.ui

import android.app.Application
import com.example.currencyconverter.domain.repository.CurrencyRepository
import com.example.currencyconverter.ui.di.ServiceLocator

class CurrencyConverterApplication: Application() {

    val currencyRepository: CurrencyRepository
        get() = ServiceLocator.provideCurrencyRepository(this)

    override fun onCreate() {
        super.onCreate()
    }
}