package com.example.currencyconverter.data

data class Currency(
    val acronym : String, // USD
    val title : String // United States dollar
)

data class Rate(
    val currencyAcronym : String,
    val cost : Double,
)

data class CurrencyRate(
    val baseCurrencyAcronym : String,
    val date : String,
    val currenciesRateList : List<Rate>
)