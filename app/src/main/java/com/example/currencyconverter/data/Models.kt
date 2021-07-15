package com.example.currencyconverter.data

data class Currency(
    val acronym : String,
    val title : String
)

data class Rate(
    val currency: Currency,
    val cost: Double,
)

data class CurrencyRate(
    val baseCurrency: Currency,
    val date : String,
    val currenciesRateList : List<Rate>
)