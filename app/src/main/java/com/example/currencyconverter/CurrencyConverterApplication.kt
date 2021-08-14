package com.example.currencyconverter

import android.app.Application
import com.example.currencyconverter.domain.repository.RepositoryImpl
import com.example.currencyconverter.di.ServiceLocator

class CurrencyConverterApplication : Application() {

    val currencyRepository: RepositoryImpl
        get() = ServiceLocator.provideCurrencyRepository(this)

}