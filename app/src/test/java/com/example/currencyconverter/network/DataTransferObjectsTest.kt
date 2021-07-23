package com.example.currencyconverter.network

import com.example.currencyconverter.database.entities.DatabaseCurrency
import com.example.currencyconverter.network.NetworkCurrencyRate
import com.example.currencyconverter.network.NetworkRate
import org.junit.Assert.*

import org.junit.Test

class DataTransferObjectsTest {

    @Test
    fun asNetworkData_mapStringString_returnListNetworkCurrency() {
        // given
        val mapStringString = mapOf<String, String>("key" to  "value")
        val networkCurrencyList = listOf(NetworkCurrency("key", "value"))
        // when
        val returnedNetworkCurrencyList = mapStringString.asNetworkData()
        // then
        assertEquals(returnedNetworkCurrencyList, networkCurrencyList)
    }

    @Test
    fun asDatabaseData_NetworkCurrency_returnListDatabaseCurrency() {
        // given
        val networkCurrencyList = listOf(NetworkCurrency("key", "value"))
        val databaseCurrencyList = listOf(DatabaseCurrency("key", "value"))
        // when
        val returnedDatabaseCurrencyList = networkCurrencyList.asDatabaseData()
        // then
        assertEquals(returnedDatabaseCurrencyList, databaseCurrencyList)
    }

    @Test
    fun asNetworkData_mapStringAny_returnNetworkCurrencyRate() {
        // given
        val mapStringAny = mapOf<String, Any>("date" to "2021-07-22" ,"eur" to mapOf<String,Double>("aed" to 4.331093))
        val networkCurrencyRate = NetworkCurrencyRate(
            date = "2021-07-22",
            baseCurrency = "eur",
            rates = listOf(NetworkRate(
                currency = "aed",
                cost = 4.331093
            ))
        )
        // when
        val returnedNetworkCurrencyRate = mapStringAny.asNetworkData()
        // then
        assertEquals(returnedNetworkCurrencyRate, networkCurrencyRate)
    }
}