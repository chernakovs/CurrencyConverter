package com.example.currencyconverter.database.entities

import com.example.currencyconverter.data.CurrencyRates
import org.junit.Assert.*

import org.junit.Test

class DatabaseCurrencyPairAndRateTest {

    @Test
    fun asDataModel_ListDatabaseCurrencyAndRate_ListCurrencyRates() {
        // given
        val databaseCurrencyAndRateList = listOf(DatabaseCurrencyAndRate(
            currencyAcronym = "acronym",
            cost = 99.999
        ))
        val currencyRatesList = listOf(CurrencyRates("acronym", 99.999))
        // when
        val returnedCurrencyRates = databaseCurrencyAndRateList.asDataModel()
        // then
        assertEquals(returnedCurrencyRates, currencyRatesList)
    }
}