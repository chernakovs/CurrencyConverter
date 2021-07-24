package com.example.currencyconverter.data.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL = "https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/"

class NetworkService {

    private val moshi = Moshi.Builder()
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()

    fun getCurrencyApi(): CurrencyApi {
        return retrofit.create(CurrencyApi::class.java)
    }

}






