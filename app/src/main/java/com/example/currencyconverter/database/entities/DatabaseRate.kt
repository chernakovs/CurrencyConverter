package com.example.currencyconverter.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "currency_rate",
    foreignKeys = [
        ForeignKey(
            entity = DatabaseCurrencyPair::class,
            parentColumns = ["id"],
            childColumns = ["currency_pair_id"],
            onDelete = ForeignKey.NO_ACTION),
    ]
)
data class DatabaseRate(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id : Long = 0L,
    @ColumnInfo(name = "currency_pair_id")
    val currencyPairId : Long,
    @ColumnInfo(name = "cost")
    val cost : Double,
    @ColumnInfo(name = "date")
    val date : String
)
