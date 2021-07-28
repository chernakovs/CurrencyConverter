package com.example.currencyconverter.data.database.entities

import androidx.room.*

@Entity(
    tableName = "currency_rate",
    indices = [Index(value = ["currency_pair_id", "date"], unique = true)],
    foreignKeys = [
        ForeignKey(
            entity = DatabaseCurrencyPair::class,
            parentColumns = ["id"],
            childColumns = ["currency_pair_id"],
            onDelete = ForeignKey.NO_ACTION
        ),
    ]
)
data class DatabaseRate(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0L,
    @ColumnInfo(name = "currency_pair_id")
    val currencyPairId: Long,
    @ColumnInfo(name = "cost")
    val cost: Double,
    @ColumnInfo(name = "date")
    val date: String
)
