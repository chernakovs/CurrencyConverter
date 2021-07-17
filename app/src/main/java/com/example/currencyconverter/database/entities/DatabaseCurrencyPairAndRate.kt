package com.example.currencyconverter.database.entities

import com.example.currencyconverter.data.Currency
import com.example.currencyconverter.data.CurrencyRate
import com.example.currencyconverter.data.Rate

data class DatabaseCurrencyPairAndRate(
    val baseCurrencyAcronym : String,
    val currencyAcronym : String,
    val cost : Double,
    val date : String
)

fun List<DatabaseCurrencyPairAndRate>.asDataModel() : CurrencyRate {
    return CurrencyRate(
        baseCurrencyAcronym = this[0].baseCurrencyAcronym,
        date = this[0].date,
        currenciesRateList = map {
            Rate(
                currencyAcronym = it.currencyAcronym,
                cost = it.cost
            )
        }
    )
}