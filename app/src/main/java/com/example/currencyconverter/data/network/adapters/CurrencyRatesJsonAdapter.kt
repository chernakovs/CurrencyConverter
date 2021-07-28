package com.example.currencyconverter.data.network.adapters

import com.example.currencyconverter.data.network.dto.NetworkRate
import com.squareup.moshi.FromJson

internal object CurrencyRatesJsonAdapter {
    @FromJson
    fun fromJson(rates: Map<String, Double>): List<NetworkRate> {
        return rates.map {
            NetworkRate(
                currency = it.key,
                cost = it.value
            )
        }
    }
}
