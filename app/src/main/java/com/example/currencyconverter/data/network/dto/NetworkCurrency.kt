package com.example.currencyconverter.data.network.dto

import com.example.currencyconverter.data.database.entities.DatabaseCurrency

data class NetworkCurrency(
    val acronym : String,
    val title : String
)

fun Map<String, String>.asNetworkData() : List<NetworkCurrency> {
    return map {
        NetworkCurrency(
            acronym = it.key,
            title = it.value
        )
    }
}

fun List<NetworkCurrency>.asDatabaseData() : List<DatabaseCurrency> {
    return map {
        DatabaseCurrency(
            acronym = it.acronym,
            title = it.title
        )
    }
}
