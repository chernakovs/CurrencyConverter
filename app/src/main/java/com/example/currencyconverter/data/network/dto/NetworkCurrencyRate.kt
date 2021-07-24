package com.example.currencyconverter.data.network.dto

data class NetworkRate(
    val currency : String,
    val cost : Double
)

data class NetworkCurrencyRate(
    val date : String,
    val baseCurrency : String,
    val rates : List<NetworkRate>
)

fun Map<String, Any>.asNetworkData() : NetworkCurrencyRate {

    val r = this.values.elementAt(1) as Map<String, Double>

    return NetworkCurrencyRate(
        date = this["date"].toString(),
        baseCurrency = this.keys.last().toString(),
        rates = r.map {
            NetworkRate(
                currency = it.key,
                cost = it.value
            )
        }
    )
}
