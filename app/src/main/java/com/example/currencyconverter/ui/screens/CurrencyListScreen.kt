package com.example.currencyconverter.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.currencyconverter.currencies.CurrencyListViewModel
import com.example.currencyconverter.data.Currency

@Composable
fun CurrencyListScreen(
    navController : NavController,
    viewModel : CurrencyListViewModel
) {

    val response = viewModel.currencies.collectAsState(initial = listOf())
    val error = viewModel.networkError.collectAsState()

    Scaffold(
        topBar = { CurrencyListTopBar() },
        content = {
            CurrencyListContent(
                response.value,
                error.value
            )
        }
    )

}

@Composable
fun CurrencyListTopBar() {
    TopAppBar(
        title = { Text(text = "Currency Converter App") },
        backgroundColor = Color.White,
        elevation = 0.dp
    )
}

@Composable
fun CurrencyListContent(
    response : List<Currency>,
    error: Boolean
) {
    if (error) {
        if (response.isNullOrEmpty()) {
            Text(text = "ERROR")
        } else {
            Text(text = response.toString())
        }
    } else {
        Text(text = response.toString())
    }
}