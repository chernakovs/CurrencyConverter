package com.example.currencyconverter.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.currencyconverter.data.database.entities.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDatabaseDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrency(currency: DatabaseCurrency)

    @Query("SELECT * FROM currency_table WHERE acronym = :acronym")
    suspend fun getCurrencyByAcronym(acronym: String): DatabaseCurrency

    @Query("SELECT * FROM currency_table WHERE acronym = :acronym")
    fun getCurrencyStateByAcronym(acronym: String): Flow<DatabaseCurrency>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrencies(currencies: List<DatabaseCurrency>)

    @Query("SELECT * FROM currency_table")
    fun getAllCurrencies(): Flow<List<DatabaseCurrency>>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCurrencyPair(pair: DatabaseCurrencyPair): Long

    @Query("SELECT * FROM currency_pair WHERE id = :id")
    suspend fun getCurrencyPairById(id: Long): DatabaseCurrencyPair

    @Query("SELECT * FROM currency_pair WHERE base_currency = :baseCurrencyAcronym AND currency = :currencyAcronym")
    suspend fun getCurrencyPairByAcronyms(
        baseCurrencyAcronym: String,
        currencyAcronym: String
    ): DatabaseCurrencyPair?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRate(rate: DatabaseRate): Long

    @Query("SELECT * FROM currency_rate WHERE id = :id")
    suspend fun getRateById(id: Long): DatabaseRate


    @Query(
        "SELECT " +
                "currency_pair.currency AS currencyAcronym, " +
                "currency_rate.cost AS cost " +
                "FROM currency_pair, currency_rate " +
                "WHERE currency_pair.base_currency = :acronym " +
                "AND currency_pair.id = currency_rate.currency_pair_id " +
                "AND currency_rate.date = (" +
                "SELECT MAX(date) FROM currency_rate, currency_pair " +
                "WHERE currency_pair.id = currency_rate.currency_pair_id " +
                "AND currency_pair.base_currency = :acronym)"
    )
    fun getLatestRatesByBaseCurrencyAcronym(acronym: String): Flow<List<DatabaseCurrencyAndRate>>


    @Query(
        "SELECT MAX(date) FROM currency_rate, currency_pair " +
                "WHERE currency_pair.id = currency_rate.currency_pair_id " +
                "AND currency_pair.base_currency = :acronym"
    )
    fun getLatestUpdateDateByCurrencyAcronym(acronym: String): Flow<String>

}