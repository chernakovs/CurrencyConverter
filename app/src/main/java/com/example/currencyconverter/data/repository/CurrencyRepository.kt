package com.example.currencyconverter.data.repository

import com.example.currencyconverter.data.database.AppDatabaseDao
import com.example.currencyconverter.data.database.entities.asDataModel
import com.example.currencyconverter.data.network.CurrencyApi
import com.example.currencyconverter.data.network.asDatabaseData
import com.example.currencyconverter.data.network.asNetworkData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class CurrencyRepository(private val database : AppDatabaseDao) {


    val currencies = database.getAllCurrencies().map {
        it.asDataModel()
    }

    suspend fun refreshCurrencies() {
        withContext(Dispatchers.IO) {
            val newCurrencies = CurrencyApi.retrofitService.getCurrencies().asNetworkData()
            database.insertCurrencies(newCurrencies.asDatabaseData())
        }
    }


}