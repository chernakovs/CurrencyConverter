package com.example.currencyconverter.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.currencyconverter.domain.Currency

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

fun DatabaseCurrency.asDataModel() : Currency {
    return Currency(
        acronym = this.acronym,
        title = this.title
    )
}