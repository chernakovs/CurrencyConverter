package com.example.currencyconverter.data.network.dto


data class NetworkCurrencyRate(
    val amount: Int,
    val base: String,
    val date: String,
    val rates: List<NetworkRate>
)

