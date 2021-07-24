package com.example.currencyconverter.ui.di

import android.content.Context
import com.example.currencyconverter.data.database.AppDatabase
import com.example.currencyconverter.data.repository.CurrencyRepository
import com.example.currencyconverter.ui.converter.utils.ValueInputValidator

object ServiceLocator {

    private var database: AppDatabase? = null

    @Volatile
    var currencyRepository: CurrencyRepository? = null

    fun provideCurrencyRepository(context: Context): CurrencyRepository {
        synchronized(this) {
            return currencyRepository ?: createCurrencyRepository(context)
        }
    }

    private fun createCurrencyRepository(context: Context): CurrencyRepository {
        val database = database ?: createDatabase(context)
        return CurrencyRepository(database.databaseDao)
    }

    private fun createDatabase(context: Context): AppDatabase {
        val result = AppDatabase.getInstance(context)
        database = result
        return result
    }

    fun provideValueInputValidator(): ValueInputValidator {
        return ValueInputValidator()
    }

}