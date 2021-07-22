package com.example.currencyconverter.data

data class Currency(
    val acronym : String, // USD
    val title : String // United States dollar
)

data class CurrencyRates(
    val currencyAcronym : String,
    val cost : Double,
    var totalValue : Double = cost
)