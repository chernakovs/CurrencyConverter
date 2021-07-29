package com.example.currencyconverter.data.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.currencyconverter.data.database.entities.DatabaseCurrency
import com.example.currencyconverter.data.database.entities.DatabaseCurrencyAndRate
import com.example.currencyconverter.data.database.entities.DatabaseCurrencyPair
import com.example.currencyconverter.data.database.entities.DatabaseRate
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {

    private lateinit var dao: AppDatabaseDao
    private lateinit var db: AppDatabase

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

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
    fun insertAndGetDatabaseCurrency() = runBlockingTest {
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
    fun insertAndGetDatabaseCurrencyFlow() = runBlockingTest {
        // given
        val newCurrency = DatabaseCurrency(
            acronym = "usd",
            title = "United States dollar"
        )
        // when
        dao.insertCurrency(newCurrency)
        val res = dao.getCurrencyStateByAcronym("usd").take(1).toList()
        // then
        assertEquals(newCurrency.acronym, res[0].acronym)
    }


    @ExperimentalCoroutinesApi
    @Test
    @Throws(Exception::class)
    fun insertAndGetDatabaseCurrenciesList() = runBlockingTest {
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
        val response = dao.getAllCurrencies().take(1).toList()
        // then
        assertEquals(currencyList, response[0])
    }


    @ExperimentalCoroutinesApi
    @Test
    @Throws(Exception::class)
    fun insertAndGetDatabaseCurrencyPair() = runBlockingTest {
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
    fun insertAndGetDatabaseRate() = runBlockingTest {
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


    @ExperimentalCoroutinesApi
    @Test
    @Throws(Exception::class)
    fun getLatestUpdateDateByCurrencyAcronym() = runBlockingTest {
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
        dao.insertCurrencies(currencyList)
        val id = dao.insertCurrencyPair(pair)
        val rate = DatabaseRate(
            currencyPairId = id,
            cost = 100.0,
            date = "2021-07-13"
        )
        val rate2 = DatabaseRate(
            currencyPairId = id,
            cost = 100.0,
            date = "2021-07-15"
        )
        dao.insertRate(rate)
        dao.insertRate(rate2)
        // when
        val response = dao.getLatestUpdateDateByCurrencyAcronym(currencyList[0].acronym).take(1).toList()
        // then
        assertEquals("2021-07-15", response[0])
    }


    @ExperimentalCoroutinesApi
    @Test
    @Throws(Exception::class)
    fun getLatestRatesByBaseCurrencyAcronym() = runBlockingTest {
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
        dao.insertCurrencies(currencyList)
        val id = dao.insertCurrencyPair(pair)
        val rate = DatabaseRate(
            currencyPairId = id,
            cost = 100.0,
            date = "2021-07-13"
        )
        dao.insertRate(rate)
        val databaseCurrencyAndRateList = listOf(DatabaseCurrencyAndRate(
            currencyAcronym = "rub",
            cost = 100.0
        ))
        // when
        val response = dao.getLatestRatesByBaseCurrencyAcronym("eur").take(1).toList()
        // then
        assertEquals(databaseCurrencyAndRateList, response[0])
    }


}