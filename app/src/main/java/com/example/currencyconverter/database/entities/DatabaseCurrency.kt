package com.example.currencyconverter.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.currencyconverter.data.Currency

@Entity(tableName = "currency_table")
data class DatabaseCurrency(
    @PrimaryKey()
    @ColumnInfo(name = "acronym")
    val acronym : String,
    @ColumnInfo(name = "title")
    val title : String
)


fun List<DatabaseCurrency>.asDataModel() : List<Currency> {
    return map {
        Currency(
            acronym = it.acronym,
            title = it.title
        )
    }
}
