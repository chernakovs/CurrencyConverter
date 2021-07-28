package com.example.currencyconverter.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency_table")
data class DatabaseCurrency(
    @PrimaryKey()
    @ColumnInfo(name = "acronym")
    val acronym : String,
    @ColumnInfo(name = "title")
    val title : String
)

