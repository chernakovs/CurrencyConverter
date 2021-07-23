package com.example.currencyconverter.database

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.currencyconverter.database.entities.DatabaseCurrency
import com.example.currencyconverter.database.entities.DatabaseCurrencyPair
import com.example.currencyconverter.database.entities.DatabaseRate
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {

    private lateinit var dao: AppDatabaseDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = db.databaseDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }


    @ExperimentalCoroutinesApi
    @Test
    @Throws(Exception::class)
    fun insertAndGetDatabaseCurrency() = runBlocking {
        // given
        val newCurrency = DatabaseCurrency(
            acronym = "usd",
            title = "United States dollar"
        )
        // when
        dao.insertCurrency(newCurrency)
        val res = dao.getCurrencyByAcronym("usd")
        // then
        assertEquals(newCurrency.acronym, res.acronym)
    }


    @ExperimentalCoroutinesApi
    @Test
    @Throws(Exception::class)
    fun insertAndGetDatabaseCurrenciesList() = runBlocking {
        // given
        val currencyList = listOf(
            DatabaseCurrency(
                acronym = "usd",
                title = "United States dollar"
            ),
            DatabaseCurrency(
                acronym = "eur",
                title = "Euro"
            ),
            DatabaseCurrency(
                acronym = "rub",
                title = "Russian ruble"
            )
        )
        // when
        dao.insertCurrencies(currencyList)
        val response = dao.getAllCurrencies()
        // then
        assertEquals(currencyList, response)
    }


    @ExperimentalCoroutinesApi
    @Test
    @Throws(Exception::class)
    fun insertAndGetDatabaseCurrencyPair() = runBlocking {
        // given
        val currencyList = listOf(
            DatabaseCurrency(
                acronym = "eur",
                title = "Euro"
            ),
            DatabaseCurrency(
                acronym = "rub",
                title = "Russian ruble"
            )
        )
        val pair = DatabaseCurrencyPair(
            baseCurrencyAcronym = currencyList[0].acronym,
            currencyAcronym = currencyList[1].acronym
        )
        // when
        dao.insertCurrencies(currencyList)
        val id = dao.insertCurrencyPair(pair)
        val byId = dao.getCurrencyPairById(id)
        val byAcronyms = dao.getCurrencyPairByAcronyms(currencyList[0].acronym, currencyList[1].acronym)
        // then
        assertEquals(byId.baseCurrencyAcronym, pair.baseCurrencyAcronym)
        assertEquals(byId.currencyAcronym, pair.currencyAcronym)
        assertEquals(byAcronyms.baseCurrencyAcronym, pair.baseCurrencyAcronym)
        assertEquals(byAcronyms.currencyAcronym, pair.currencyAcronym)
    }


    @ExperimentalCoroutinesApi
    @Test
    @Throws(Exception::class)
    fun insertAndGetDatabaseRate() = runBlocking {
        // given
        val currencyList = listOf(
            DatabaseCurrency(
                acronym = "eur",
                title = "Euro"
            ),
            DatabaseCurrency(
                acronym = "rub",
                title = "Russian ruble"
            )
        )
        val pair = DatabaseCurrencyPair(
            baseCurrencyAcronym = currencyList[0].acronym,
            currencyAcronym = currencyList[1].acronym
        )
        // when
        dao.insertCurrencies(currencyList)
        val id = dao.insertCurrencyPair(pair)
        val rate = DatabaseRate(
            currencyPairId = id,
            cost = 100.0,
            date = "2021-07-13"
        )
        val rateId = dao.insertRate(rate)
        val res = dao.getRateById(rateId)
        // then
        assertEquals(res.currencyPairId, id)
        assertEquals(res.date, rate.date)
    }

}