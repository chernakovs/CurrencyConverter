package com.example.currencyconverter.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.currencyconverter.database.entities.DatabaseCurrency
import com.example.currencyconverter.database.entities.DatabaseCurrencyPair
import com.example.currencyconverter.database.entities.DatabaseRate

@Database(entities = [
    DatabaseCurrency::class,
    DatabaseCurrencyPair::class,
    DatabaseRate::class
],
    version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract val databaseDao: AppDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "currency_converter_database")
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }

    }

}