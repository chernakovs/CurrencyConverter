package com.example.currencyconverter.domain.repository

import com.example.currencyconverter.data.database.AppDatabaseDao
import com.example.currencyconverter.data.network.CurrencyApi
import com.example.currencyconverter.domain.Repository
import com.example.currencyconverter.domain.model.Currency
import com.example.currencyconverter.domain.model.CurrencyPair
import com.example.currencyconverter.domain.model.CurrencyRates
import com.example.currencyconverter.mappers.CurrencyDatabaseMapper
import com.example.currencyconverter.mappers.CurrencyDomainMapper
import com.example.currencyconverter.mappers.CurrencyNetworkMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class RepositoryImpl(
    private val database: AppDatabaseDao,
    private val apiService: CurrencyApi,
    private val databaseMapper: CurrencyDatabaseMapper,
    private val networkMapper: CurrencyNetworkMapper,
    private val domainMapper: CurrencyDomainMapper
) : Repository {


    override fun getRatesByBaseAcronym(acronym: String): Flow<List<CurrencyRates>> {
        return database.getLatestRatesByBaseCurrencyAcronym(acronym).map {
            databaseMapper.databaseCurrencyAndRateListMapToDomain(it)
        }
    }

    override fun getBaseCurrencyLatestUpdateDate(acronym: String): Flow<String> {
        return database.getLatestUpdateDateByCurrencyAcronym(acronym)
    }

    override fun getCurrency(acronym: String): Flow<Currency> {
        return database.getCurrencyStateByAcronym(acronym)
            .map { databaseMapper.currencyMapToDomain(it) }
    }


    override fun getAllCurrencies(): Flow<List<Currency>> {
        return database.getAllCurrencies().map {
            databaseMapper.currencyListMapToDomain(it)
        }
    }

    override suspend fun refreshCurrenciesList() {
        withContext(Dispatchers.IO) {
            val newCurrencies = apiService.getCurrencies()
            database.insertCurrencies(networkMapper.networkCurrencyMapToDatabase(newCurrencies))
        }
    }

    override suspend fun refreshCurrencyRates(acronym: String) {
        withContext(Dispatchers.IO) {

            val currencyRates = apiService.getRates(acronym)

            currencyRates.rates.map {

                val pairId = database.getCurrencyPairByAcronyms(currencyRates.base, it.currency)?.id
                    ?: database.insertCurrencyPair(
                        domainMapper.currencyPairMapToDatabase(
                            CurrencyPair(
                                baseCurrencyAcronym = currencyRates.base,
                                currencyAcronym = it.currency
                            )
                        )
                    )

                database.insertRate(
                    domainMapper.toDatabaseRate(
                        pairId,
                        it.cost,
                        currencyRates.date
                    )
                )

            }

        }
    }
}