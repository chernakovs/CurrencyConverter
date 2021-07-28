package com.example.currencyconverter.mappers

import com.example.currencyconverter.data.database.entities.DatabaseCurrency
import com.example.currencyconverter.data.network.dto.NetworkCurrency

class CurrencyNetworkMapper() {

    fun networkCurrencyMapToDatabase(currencies: List<NetworkCurrency>): List<DatabaseCurrency> =
        currencies.map {
            DatabaseCurrency(
                acronym = it.acronym,
                title = it.title
            )
        }

}

