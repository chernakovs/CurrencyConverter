package com.example.currencyconverter.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.currencyconverter.currencies.CurrencyListViewModel
import com.example.currencyconverter.data.Currency

@ExperimentalMaterialApi
@Composable
fun CurrencyListScreen(
    navController : NavController,
    viewModel : CurrencyListViewModel
) {

    val currenciesList = viewModel.currencies.collectAsState(initial = listOf())
    val networkError = viewModel.networkError.collectAsState()

    Scaffold(
        topBar = { CurrencyListTopBar() },
        content = {
            CurrencyListContent(
                currenciesList.value,
                networkError.value
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

@ExperimentalMaterialApi
@Composable
fun CurrencyListContent(
    currenciesList : List<Currency>,
    networkError: Boolean
) {
    if (networkError) {
        if (currenciesList.isNullOrEmpty()) {
            Text(text = "NETWORK ERROR")
        } else {
            CurrencyList(currenciesList)
        }
    } else {
        CurrencyList(currenciesList)
    }
}


@ExperimentalMaterialApi
@Composable
fun CurrencyList(
    currenciesList : List<Currency>
) {

    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(currenciesList) { currency ->
            CurrencyCard(currency = currency)
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun CurrencyCard(
    currency : Currency
) {

    Card(
        onClick = {}
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(32.dp),
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(0.9f)
                    .wrapContentWidth(Alignment.Start)
            ) {
                Text(text = currency.acronym.uppercase())
                Text(text = currency.title)
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .wrapContentWidth(Alignment.End)
                    .fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Outlined.KeyboardArrowRight,
                    contentDescription = "back button",
                    tint = Color.Black
                )
            }
        }
    }

}