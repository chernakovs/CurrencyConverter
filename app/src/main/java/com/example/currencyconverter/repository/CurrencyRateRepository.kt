package com.example.currencyconverter.repository

import android.util.Log
import com.example.currencyconverter.data.Currency
import com.example.currencyconverter.database.AppDatabaseDao
import com.example.currencyconverter.database.entities.DatabaseCurrency
import com.example.currencyconverter.database.entities.DatabaseCurrencyPair
import com.example.currencyconverter.database.entities.DatabaseRate
import com.example.currencyconverter.database.entities.asDataModel
import com.example.currencyconverter.network.CurrencyApi
import com.example.currencyconverter.network.asNetworkData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class CurrencyRateRepository(
    private val database : AppDatabaseDao,
    private val acronym : String
    ) {


    val rates = database.getLatestRatesByBaseCurrencyAcronym(acronym).map {
            it.asDataModel()
   }

    val latestDate = database.getLatestUpdateDateByCurrencyAcronym(acronym)

    val baseCurrency = database.getCurrencyStateByAcronym(acronym).map { it.asDataModel() }


    suspend fun refreshCurrencyRates() {
        withContext(Dispatchers.IO) {

            val newCurrencyRates = CurrencyApi.retrofitService.getRates(acronym).asNetworkData()

            newCurrencyRates.rates.map {

                if (database.getCurrencyByAcronym(it.currency) != null) { /** bug in API: not all currencies are represented in the /latest/currencies.json response **/

                    var pairId = database.insertCurrencyPair(
                        DatabaseCurrencyPair(
                            baseCurrencyAcronym = newCurrencyRates.baseCurrency,
                            currencyAcronym = it.currency
                        )
                    )
                    if (pairId == -1L) {
                        pairId = database.getCurrencyPairByAcronyms(
                            newCurrencyRates.baseCurrency, it.currency
                        ).id
                    }
                    database.insertRate(
                        DatabaseRate(
                            currencyPairId = pairId,
                            cost = it.cost,
                            date = newCurrencyRates.date
                        )
                    )
                }
            }
        }
    }
}