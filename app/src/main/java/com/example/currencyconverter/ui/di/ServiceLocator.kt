package com.example.currencyconverter.ui.di

import android.content.Context
import com.example.currencyconverter.data.database.AppDatabase
import com.example.currencyconverter.data.network.NetworkService
import com.example.currencyconverter.domain.repository.CurrencyRepository
import com.example.currencyconverter.mappers.CurrencyDatabaseMapper
import com.example.currencyconverter.mappers.CurrencyDomainMapper
import com.example.currencyconverter.mappers.CurrencyNetworkMapper
import com.example.currencyconverter.ui.converter.utils.ValueInputValidator

object ServiceLocator {

    private var database: AppDatabase? = null

    private val networkService by lazy {
        NetworkService()
    }

    private val domainMapper by lazy {
        CurrencyDomainMapper()
    }

    private val networkMapper by lazy {
        CurrencyNetworkMapper()
    }

    private val databaseMapper by lazy {
        CurrencyDatabaseMapper()
    }


    private var valueInputValidator : ValueInputValidator? = null

    @Volatile
    private var currencyRepository: CurrencyRepository? = null

    fun provideCurrencyRepository(context: Context): CurrencyRepository {
        synchronized(this) {
            return currencyRepository ?: createCurrencyRepository(context)
        }
    }

    private fun createCurrencyRepository(context: Context): CurrencyRepository {
        val database = database ?: createDatabase(context)
        return CurrencyRepository(
            database.databaseDao,
            networkService.getCurrencyApi(),
            databaseMapper,
            networkMapper,
            domainMapper
        )
    }

    private fun createDatabase(context: Context): AppDatabase {
        val result = AppDatabase.getInstance(context)
        database = result
        return result
    }

    fun provideValueInputValidator(): ValueInputValidator {
        return valueInputValidator ?: ValueInputValidator()
    }

}