package com.example.currencyconverter.database.entities

import com.example.currencyconverter.data.Currency
import org.junit.Assert.*

import org.junit.Test

class DatabaseCurrencyTest {

    @Test
    fun asDataModel_ListDatabaseCurrency_ListCurrency() {
        // given
        val databaseCurrencyList = listOf(DatabaseCurrency("acronym", "title"))
        val currencyList = listOf(Currency("acronym", "title"))
        // when
        val returnedCurrencyList = databaseCurrencyList.asDataModel()
        // then
        assertEquals(returnedCurrencyList, currencyList)
    }

    @Test
    fun asDataModel_DatabaseCurrency_Currency() {
        // given
        val databaseCurrency = DatabaseCurrency("acronym", "title")
        val currency = Currency("acronym", "title")
        // when
        val returnedCurrency = databaseCurrency.asDataModel()
        // then
        assertEquals(returnedCurrency, currency)
    }
}