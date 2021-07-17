package com.example.currencyconverter.database.entities

import androidx.room.*

@Entity(
    tableName = "currency_pair",
//    primaryKeys = ["base_currency", "currency"]
    foreignKeys = [
        ForeignKey(
            entity = DatabaseCurrency::class,
            parentColumns = ["acronym"],
            childColumns = ["base_currency"],
            onDelete = ForeignKey.NO_ACTION),
        ForeignKey(
            entity = DatabaseCurrency::class,
            parentColumns = ["acronym"],
            childColumns = ["currency"],
            onDelete = ForeignKey.NO_ACTION)
    ]
)
data class DatabaseCurrencyPair(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id : Long = 0L,
    @ColumnInfo(name = "base_currency")
    val baseCurrencyAcronym : String,
    @ColumnInfo(name = "currency")
    val currencyAcronym : String,
)
