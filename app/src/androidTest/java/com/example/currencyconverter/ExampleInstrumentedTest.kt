package com.example.currencyconverter

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

import androidx.room.Room
import com.example.currencyconverter.data.Currency
import com.example.currencyconverter.data.CurrencyRate
import com.example.currencyconverter.data.Rate
import com.example.currencyconverter.database.AppDatabase
import com.example.currencyconverter.database.AppDatabaseDao
import com.example.currencyconverter.database.entities.DatabaseCurrency
import com.example.currencyconverter.database.entities.DatabaseCurrencyPair
import com.example.currencyconverter.database.entities.DatabaseRate
import com.example.currencyconverter.database.entities.asDataModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.After
import org.junit.Before
import java.io.IOException

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */



@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {

    private lateinit var dao: AppDatabaseDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
                // Allowing main thread queries, just for testing.
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

        val newCurrency = DatabaseCurrency(
            acronym = "usd",
            title = "United States dollar"
        )
        dao.insertCurrency(newCurrency)

        val res = dao.getCurrencyByAcronym("usd")

        assertEquals(newCurrency.acronym, res.acronym)
    }

    @ExperimentalCoroutinesApi
    @Test
    @Throws(Exception::class)
    fun insertAndGetDatabaseCurrenciesList() = runBlocking {

        val list = listOf<DatabaseCurrency>(
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

        dao.insertCurrencies(list)

        val res = dao.getAllCurrencies()

        assertEquals(list, res)
    }

    @ExperimentalCoroutinesApi
    @Test
    @Throws(Exception::class)
    fun insertAndGetDatabaseCurrencyPair() = runBlocking {

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

        val res = dao.getCurrencyPairById(id)

        assertEquals(res.baseCurrencyAcronym, pair.baseCurrencyAcronym)
        assertEquals(res.currencyAcronym, pair.currencyAcronym)

    }

    @ExperimentalCoroutinesApi
    @Test
    @Throws(Exception::class)
    fun insertAndGetDatabaseRate() = runBlocking {

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

        val rateId = dao.insertRate(rate)

        val res = dao.getRateById(rateId)

        assertEquals(res.currencyPairId, id)
        assertEquals(res.date, rate.date)


    }


    @ExperimentalCoroutinesApi
    @Test
    @Throws(Exception::class)
    fun getCurrencyPairAndRateList() = runBlocking {

        val currencyList = listOf(
            DatabaseCurrency(
                acronym = "eur",
                title = "Euro"
            ),
            DatabaseCurrency(
                acronym = "usd",
                title = "United states dollar"
            ),
            DatabaseCurrency(
                acronym = "rub",
                title = "Russian ruble"
            )
        )
        val pair1 = DatabaseCurrencyPair(
            baseCurrencyAcronym = currencyList[0].acronym,
            currencyAcronym = currencyList[1].acronym
        )
        val pair2 = DatabaseCurrencyPair(
            baseCurrencyAcronym = currencyList[0].acronym,
            currencyAcronym = currencyList[2].acronym
        )


        dao.insertCurrencies(currencyList)
        val pair1Id = dao.insertCurrencyPair(pair1)
        val pair2Id = dao.insertCurrencyPair(pair2)

        val rate1 = DatabaseRate(
            currencyPairId = pair1Id,
            cost = 100.0,
            date = "2021-07-13"
        )
        val rate2 = DatabaseRate(
            currencyPairId = pair2Id,
            cost = 99.0,
            date = "2021-07-13"
        )
        val rate3 = DatabaseRate(
            currencyPairId = pair1Id,
            cost = 89.0,
            date = "2021-07-12"
        )


        val rate1Id = dao.insertRate(rate1)
        val rate2Id = dao.insertRate(rate2)
        val rate3Id = dao.insertRate(rate3)

        val res = dao.getLatestRatesByBaseCurrencyAcronym("eur")

        assertEquals(res.size, 2)
        assertEquals(res[0].baseCurrencyAcronym, "eur")
        assertEquals(res[1].baseCurrencyAcronym, "eur")
        assertEquals(res[0].currencyAcronym, "usd")
        assertEquals(res[1].currencyAcronym, "rub")
        assertEquals(res[0].date, "2021-07-13")
        assertEquals(res[1].date, "2021-07-13")
        assertEquals(res[0].cost, 100.0, 0.0)
        assertEquals(res[1].cost, 99.0, 0.0)


    }

    @ExperimentalCoroutinesApi
    @Test
    @Throws(Exception::class)
    fun testCurrencyPairAndRateListToCurrencyRateConverter() = runBlocking {

        val currencyList = listOf(
            DatabaseCurrency(
                acronym = "eur",
                title = "Euro"
            ),
            DatabaseCurrency(
                acronym = "usd",
                title = "United states dollar"
            ),
            DatabaseCurrency(
                acronym = "rub",
                title = "Russian ruble"
            )
        )
        val pair1 = DatabaseCurrencyPair(
            baseCurrencyAcronym = currencyList[0].acronym,
            currencyAcronym = currencyList[1].acronym
        )
        val pair2 = DatabaseCurrencyPair(
            baseCurrencyAcronym = currencyList[0].acronym,
            currencyAcronym = currencyList[2].acronym
        )


        dao.insertCurrencies(currencyList)
        val pair1Id = dao.insertCurrencyPair(pair1)
        val pair2Id = dao.insertCurrencyPair(pair2)

        val rate1 = DatabaseRate(
            currencyPairId = pair1Id,
            cost = 100.0,
            date = "2021-07-13"
        )
        val rate2 = DatabaseRate(
            currencyPairId = pair2Id,
            cost = 99.0,
            date = "2021-07-13"
        )
        val rate3 = DatabaseRate(
            currencyPairId = pair1Id,
            cost = 89.0,
            date = "2021-07-12"
        )


        val rate1Id = dao.insertRate(rate1)
        val rate2Id = dao.insertRate(rate2)
        val rate3Id = dao.insertRate(rate3)

        val res = dao.getLatestRatesByBaseCurrencyAcronym("eur").asDataModel()

        val currencyRate = CurrencyRate(
            baseCurrencyAcronym = "eur",
            date = "2021-07-13",
            currenciesRateList = listOf(
                Rate("usd", 100.0),
                Rate("rub", 99.0)
            )
        )

        assertEquals(res, currencyRate)


    }


    @ExperimentalCoroutinesApi
    @Test
    @Throws(Exception::class)
    fun testDatabaseCurrencyListToCurrencyListConverter() = runBlocking {

        val currencyList = listOf(
            DatabaseCurrency(
                acronym = "eur",
                title = "Euro"
            ),
            DatabaseCurrency(
                acronym = "usd",
                title = "United states dollar"
            ),
            DatabaseCurrency(
                acronym = "rub",
                title = "Russian ruble"
            )
        )


        dao.insertCurrencies(currencyList)


        val res = dao.getAllCurrencies().asDataModel()

        val currencies = listOf(
            Currency(
                acronym = "eur",
                title = "Euro"
            ),
            Currency(
                acronym = "usd",
                title = "United states dollar"
            ),
            Currency(
                acronym = "rub",
                title = "Russian ruble"
            )
        )

        assertEquals(res, currencies)


    }


}
