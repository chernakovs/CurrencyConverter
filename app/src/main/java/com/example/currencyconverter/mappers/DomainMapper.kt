package com.example.currencyconverter.mappers

import com.example.currencyconverter.data.database.entities.DatabaseCurrencyPair
import com.example.currencyconverter.data.database.entities.DatabaseRate
import com.example.currencyconverter.domain.model.CurrencyPair

class CurrencyDomainMapper {

    fun currencyPairMapToDatabase(pair: CurrencyPair): DatabaseCurrencyPair =
        DatabaseCurrencyPair(baseCurrencyAcronym = pair.baseCurrencyAcronym, currencyAcronym =  pair.currencyAcronym)

    fun toDatabaseRate(pairId: Long, cost: Double, date: String): DatabaseRate =
        DatabaseRate(currencyPairId = pairId, cost = cost, date = date)

}