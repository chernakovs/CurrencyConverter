package com.example.currencyconverter.data.network

import retrofit2.http.GET
import retrofit2.http.Path

interface CurrencyApi {

    @GET("latest/currencies.json")
    suspend fun getCurrencies(): Map<String, String>

    @GET("latest/currencies/{currency}.json")
    suspend fun getRates(@Path(value="currency") currency : String) : Map<String, Any>

}
