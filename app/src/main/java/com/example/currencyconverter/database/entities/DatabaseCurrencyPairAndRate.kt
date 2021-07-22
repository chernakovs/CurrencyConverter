package com.example.currencyconverter.database.entities

import com.example.currencyconverter.data.Currency
import com.example.currencyconverter.data.CurrencyRates

data class DatabaseCurrencyAndRate(
    val currencyAcronym : String,
    val cost : Double,
)


fun List<DatabaseCurrencyAndRate>.asDataModel() : List<CurrencyRates> {
    return map {
        CurrencyRates(
            currencyAcronym = it.currencyAcronym,
            cost = it.cost
        )
    }
}