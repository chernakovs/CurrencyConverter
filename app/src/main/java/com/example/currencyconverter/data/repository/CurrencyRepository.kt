package com.example.currencyconverter.data.repository

import com.example.currencyconverter.data.database.AppDatabaseDao
import com.example.currencyconverter.data.database.entities.DatabaseCurrencyPair
import com.example.currencyconverter.data.database.entities.DatabaseRate
import com.example.currencyconverter.data.database.entities.asDataModel
import com.example.currencyconverter.data.network.CurrencyApi
import com.example.currencyconverter.data.network.dto.asDatabaseData
import com.example.currencyconverter.data.network.dto.asNetworkData
import com.example.currencyconverter.domain.Currency
import com.example.currencyconverter.domain.CurrencyRates
import com.example.currencyconverter.domain.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class CurrencyRepository(
    private val database: AppDatabaseDao,
    private val apiService: CurrencyApi
) : Repository {

    override fun getRatesByBaseAcronym(acronym: String): Flow<List<CurrencyRates>> {
        return database.getLatestRatesByBaseCurrencyAcronym(acronym).map {
            it.asDataModel()
        }
    }

    override fun getBaseCurrencyLatestUpdateDate(acronym: String): Flow<String> {
        return database.getLatestUpdateDateByCurrencyAcronym(acronym)
    }

    override fun getCurrency(acronym: String): Flow<Currency> {
        return database.getCurrencyStateByAcronym(acronym).map { it.asDataModel() }
    }


    override fun getAllCurrencies(): Flow<List<Currency>> {
        return database.getAllCurrencies().map {
            it.asDataModel()
        }
    }

    override suspend fun refreshCurrenciesList() {
        withContext(Dispatchers.IO) {
            val newCurrencies = apiService.getCurrencies().asNetworkData()
            database.insertCurrencies(newCurrencies.asDatabaseData())
        }
    }

    override suspend fun refreshCurrencyRates(acronym : String) {
        withContext(Dispatchers.IO) {

            val newCurrencyRates = apiService.getRates(acronym).asNetworkData()

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