package com.example.currencyconverter.data.database.entities

import com.example.currencyconverter.domain.CurrencyRates

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