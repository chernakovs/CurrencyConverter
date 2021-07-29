package com.example.currencyconverter.data.network.adapters

import com.example.currencyconverter.data.network.dto.NetworkCurrency
import com.squareup.moshi.FromJson

internal object CurrenciesJsonAdapter {
    @FromJson
    fun fromJson(response: Map<String, String>): List<NetworkCurrency> {
        return response.map {
            NetworkCurrency(
                acronym = it.key,
                title = it.value
            )
        }
    }
}
