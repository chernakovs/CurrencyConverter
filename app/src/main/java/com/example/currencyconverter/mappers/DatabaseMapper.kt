package com.example.currencyconverter.mappers

import com.example.currencyconverter.data.database.entities.DatabaseCurrency
import com.example.currencyconverter.data.database.entities.DatabaseCurrencyAndRate
import com.example.currencyconverter.domain.model.Currency
import com.example.currencyconverter.domain.model.CurrencyRates


class CurrencyDatabaseMapper() {

    fun currencyMapToDomain(currency: DatabaseCurrency): Currency =
        Currency(
            acronym = currency.acronym,
            title = currency.title
        )

    fun currencyListMapToDomain(currencies: List<DatabaseCurrency>): List<Currency> =
        currencies.map {
            Currency(
                acronym = it.acronym,
                title = it.title
            )
        }

    fun databaseCurrencyAndRateListMapToDomain(rates: List<DatabaseCurrencyAndRate>) : List<CurrencyRates> =
        rates.map {
            CurrencyRates(
                currencyAcronym = it.currencyAcronym,
                cost = it.cost
            )
        }


}

