package com.example.currencyconverter.data.network

import com.example.currencyconverter.data.network.adapters.CurrenciesJsonAdapter
import com.example.currencyconverter.data.network.adapters.CurrencyRatesJsonAdapter
import com.squareup.moshi.*
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.Retrofit

private const val BASE_URL = "https://api.frankfurter.app"

class NetworkService {

    private val moshi = Moshi.Builder()
        .add(CurrenciesJsonAdapter)
        .add(CurrencyRatesJsonAdapter)
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()

    fun getCurrencyApi(): CurrencyApi {
        return retrofit.create(CurrencyApi::class.java)
    }

}






