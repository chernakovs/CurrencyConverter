package com.example.currencyconverter.data.network

import com.example.currencyconverter.data.network.dto.NetworkCurrency
import com.example.currencyconverter.data.network.dto.NetworkCurrencyRate
import retrofit2.http.GET
import retrofit2.http.Query


interface CurrencyApi {

    @GET("/currencies")
    suspend fun getCurrencies(): List<NetworkCurrency>

    @GET("/latest")
    suspend fun getRates(@Query(value="from") currency : String) : NetworkCurrencyRate

}