package com.example.currencyconverter.repository

import androidx.lifecycle.Transformations
import com.example.currencyconverter.data.Currency
import com.example.currencyconverter.database.AppDatabaseDao
import com.example.currencyconverter.database.entities.asDataModel
import com.example.currencyconverter.network.CurrencyApi
import com.example.currencyconverter.network.asDatabaseData
import com.example.currencyconverter.network.asNetworkData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher

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