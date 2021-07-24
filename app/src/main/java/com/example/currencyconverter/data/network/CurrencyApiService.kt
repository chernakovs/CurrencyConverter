package com.example.currencyconverter.data.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL = "https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/"

private val moshi = Moshi.Builder()
    .build()

val adapter = moshi.adapter<Map<String, String>>(
    Types.newParameterizedType(Map::class.java, String::class.java, String::class.java)
)

val adapter2 = moshi.adapter<Map<String, Any>>(
    Types.newParameterizedType(Map::class.java, String::class.java, Any::class.java)
)

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface CurrencyApiService {

    @GET("latest/currencies.json")
    suspend fun getCurrencies(): Map<String, String>

    @GET("latest/currencies/{currency}.json")
    suspend fun getRates(@Path(value="currency") currency : String) : Map<String, Any>

}

object CurrencyApi {
    val retrofitService : CurrencyApiService by lazy {
        retrofit.create(CurrencyApiService::class.java)
    }
}


