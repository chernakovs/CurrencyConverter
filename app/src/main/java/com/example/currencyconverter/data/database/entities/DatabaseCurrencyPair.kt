package com.example.currencyconverter.data.database.entities

import androidx.room.*

@Entity(
    tableName = "currency_pair",
    indices = [Index(value = ["base_currency", "currency"], unique = true)],
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
