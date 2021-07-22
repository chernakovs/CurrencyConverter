package com.example.currencyconverter.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.currencyconverter.Screen
import com.example.currencyconverter.converter.CurrencyConverterViewModel
import com.example.currencyconverter.data.Currency
import com.example.currencyconverter.data.CurrencyRates

@ExperimentalComposeUiApi
@Composable
fun ConverterScreen(
    navController : NavController,
    viewModel : CurrencyConverterViewModel
) {

    val rates = viewModel.rates.collectAsState(initial = listOf())
    val networkError = viewModel.networkError.collectAsState()
    val inputError = viewModel.valueInputError.collectAsState()
    val value = viewModel.valueString.collectAsState()
    val setValue = viewModel::setValue
    val searchQuery = viewModel.searchQuery.collectAsState()
    val setSearchQuery = viewModel::setSearchQuery
    val baseCurrency = viewModel.baseCurrency.collectAsState(initial = Currency("", ""))
    val latestDate = viewModel.latestUpdateDate.collectAsState("")

    Scaffold(
        topBar = { ConverterTopBar(
            navController = navController,
            currencyTitle = baseCurrency.value.title
        ) },
        content = {
            ConverterContent(
                rates.value,
                networkError.value,
                inputError.value,
                value.value,
                setValue,
                searchQuery.value,
                setSearchQuery,
                if (!latestDate.value.isNullOrEmpty()) latestDate.value else "",
                baseCurrency.value.acronym
            )
        }
    )

}

@Composable
fun ConverterTopBar(
    navController : NavController,
    currencyTitle : String = "Test"
) {
    TopAppBar(
        navigationIcon = { NavigationIconButton(navController = navController) },
        title = { Text(text = currencyTitle) },
        backgroundColor = Color.White,
        elevation = 0.dp
    )
}

@Composable
fun NavigationIconButton(
    navController : NavController
) {
    IconButton(
        onClick = {
            navController.popBackStack(Screen.Converter.route, inclusive = true)
        }
    ) {
        Icon(
            imageVector = Icons.Outlined.ArrowBack,
            contentDescription = "back button",
            tint = Color.Black
        )
    }
}

@ExperimentalComposeUiApi
@Composable
fun ConverterContent(
    rates : List<CurrencyRates>,
    networkError : Boolean,
    inputError : Boolean,
    value : String,
    setValue : (String) -> Unit,
    searchQuery: String,
    setSearchQuery: (String) -> Unit,
    latestDate : String,
    baseCurrencyAcronym : String
) {
    Column {

        BaseCurrencyCard(baseCurrencyAcronym, latestDate, value, inputError, setValue, searchQuery, setSearchQuery)

        SearchRow(searchQuery = searchQuery, setSearchQuery = setSearchQuery)


        if (!rates.isNullOrEmpty()) {
                CurrencyRatesList(rates)
        } else {
            if (networkError) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "NETWORK ERROR")
                }
            }
        }

    }

}

@Composable
fun CurrencyRatesList(
    rates : List<CurrencyRates>
) {

    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(rates) { rate ->
            CurrencyRateCard(rate)
        }
    }
}


@Composable
fun CurrencyRateCard(
    rate : CurrencyRates,
) {

    Card() {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(0.5f)
                    .wrapContentWidth(Alignment.Start)
            ) {
                Text(text = rate.currencyAcronym.uppercase())
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .wrapContentWidth(Alignment.End)
                    .fillMaxWidth()
            ) {
                Text(text = rate.totalValue.toString())
            }
        }

    }

}


@ExperimentalComposeUiApi
@Composable
fun BaseCurrencyCard(
    baseCurrencyAcronym : String,
    date : String,
    value : String,
    inputError : Boolean,
    setValue : (String) -> Unit,
    searchQuery : String,
    setSearchQuery : (String) -> Unit
) {

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current


    Card(
        elevation = 8.dp,
        modifier = Modifier
            .padding(16.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Rates by $date",
                    fontSize = 10.sp,
                    modifier = Modifier
                        .padding(top = 8.dp, start = 16.dp)
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(0.5f)
                        .wrapContentWidth(Alignment.Start)
                ) {
                    Text(
                        text = baseCurrencyAcronym.uppercase(),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .wrapContentWidth(Alignment.End)
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    OutlinedTextField(
                        value = value,
                        onValueChange = { setValue(it) },
                        isError = inputError,
                        label = { Text(text = "VALUE")},
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done,
                            capitalization = KeyboardCapitalization.Sentences,
                            keyboardType = KeyboardType.Number
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                focusManager.clearFocus()
                                keyboardController?.hide()
                            }
                        ),
                    )
                }
            }

//            SearchRow(searchQuery = searchQuery, setSearchQuery = setSearchQuery)

        }

    }

}