package com.example.currencyconverter.domain.model

data class CurrencyRates(
    val currencyAcronym: String,
    val cost: Double,
    var totalValue: Double = cost
)