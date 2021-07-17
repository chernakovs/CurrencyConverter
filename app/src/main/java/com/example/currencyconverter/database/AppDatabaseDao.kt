package com.example.currencyconverter.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.currencyconverter.database.entities.DatabaseCurrency
import com.example.currencyconverter.database.entities.DatabaseCurrencyPair
import com.example.currencyconverter.database.entities.DatabaseCurrencyPairAndRate
import com.example.currencyconverter.database.entities.DatabaseRate
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDatabaseDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrency(currency: DatabaseCurrency)

    @Query("SELECT * FROM currency_table WHERE acronym = :acronym")
    suspend fun getCurrencyByAcronym(acronym : String) : DatabaseCurrency



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrencies(currencies : List<DatabaseCurrency>)

    @Query("SELECT * FROM currency_table")
//    suspend fun getAllCurrencies() : List<DatabaseCurrency>
     fun getAllCurrencies() : Flow<List<DatabaseCurrency>>



    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCurrencyPair(pair : DatabaseCurrencyPair) : Long

    @Query("SELECT * FROM currency_pair WHERE id = :id")
    suspend fun getCurrencyPairById(id : Long) : DatabaseCurrencyPair



    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRate(rate : DatabaseRate) : Long

    @Query("SELECT * FROM currency_rate WHERE id = :id")
    suspend fun getRateById(id: Long) : DatabaseRate



    @Query("SELECT " +
            "currency_pair.base_currency AS baseCurrencyAcronym, " +
            "currency_pair.currency AS currencyAcronym, " +
            "currency_rate.cost AS cost, currency_rate.date AS date " +
            "FROM currency_pair, currency_rate " +
            "WHERE currency_pair.base_currency = :acronym " +
            "AND currency_pair.id = currency_rate.currency_pair_id " +
            "AND currency_rate.date = (" +
                "SELECT MAX(date) FROM currency_rate)"
    )
    suspend fun getLatestRatesByBaseCurrencyAcronym(acronym : String) : List<DatabaseCurrencyPairAndRate>

}