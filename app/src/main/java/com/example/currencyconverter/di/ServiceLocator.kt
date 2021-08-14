package com.example.currencyconverter.di

import android.content.Context
import com.example.currencyconverter.data.database.AppDatabase
import com.example.currencyconverter.data.database.AppDatabaseDao
import com.example.currencyconverter.data.network.NetworkService
import com.example.currencyconverter.domain.repository.RepositoryImpl
import com.example.currencyconverter.mappers.CurrencyDatabaseMapper
import com.example.currencyconverter.mappers.CurrencyDomainMapper
import com.example.currencyconverter.mappers.CurrencyNetworkMapper
import com.example.currencyconverter.ui.converter.utils.ValueInputValidator

object ServiceLocator {

    private var valueInputValidator: ValueInputValidator? = null

    private var currencyRepository: RepositoryImpl? = null

    fun provideCurrencyRepository(context: Context): RepositoryImpl {
        return currencyRepository ?: createCurrencyRepository(context)
    }

    private fun createCurrencyRepository(context: Context): RepositoryImpl {
        val repository = RepositoryImpl(
            provideDatabaseDao(context),
            NetworkService().getCurrencyApi(),
            CurrencyDatabaseMapper(),
            CurrencyNetworkMapper(),
            CurrencyDomainMapper()
        )
        currencyRepository = repository
        return repository
    }

    private fun provideDatabaseDao(context: Context): AppDatabaseDao {
        return AppDatabase.getInstance(context).databaseDao
    }

    fun provideValueInputValidator(): ValueInputValidator {
        return valueInputValidator ?: createValueInputValidator()
    }

    private fun createValueInputValidator(): ValueInputValidator {
        val validator = ValueInputValidator()
        valueInputValidator = validator
        return validator
    }

}