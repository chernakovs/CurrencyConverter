package com.example.currencyconverter.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun CurrencyListScreen(
    navController : NavController
) {

    Scaffold(
        topBar = { CurrencyListTopBar() },
        content = { CurrencyListContent() }
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
fun CurrencyListContent() {

}