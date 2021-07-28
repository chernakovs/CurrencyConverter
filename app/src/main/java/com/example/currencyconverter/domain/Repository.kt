package com.example.currencyconverter.domain

import com.example.currencyconverter.domain.model.Currency
import com.example.currencyconverter.domain.model.CurrencyRates
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun getRatesByBaseAcronym(acronym: String): Flow<List<CurrencyRates>>

    fun getBaseCurrencyLatestUpdateDate(acronym: String): Flow<String>

    fun getCurrency(acronym: String): Flow<Currency>

    fun getAllCurrencies(): Flow<List<Currency>>

    suspend fun refreshCurrenciesList()

    suspend fun refreshCurrencyRates(acronym : String)

}