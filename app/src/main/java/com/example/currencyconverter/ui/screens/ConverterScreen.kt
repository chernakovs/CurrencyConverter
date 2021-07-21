package com.example.currencyconverter.ui.screens

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.currencyconverter.Screen
import com.example.currencyconverter.converter.CurrencyConverterViewModel
import com.example.currencyconverter.data.CurrencyRates

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

    Scaffold(
        topBar = { ConverterTopBar(navController = navController) },
        content = {
            ConverterContent(
                rates.value,
                networkError.value,
                inputError.value,
                value.value,
                setValue
            )
        }
    )

}

@Composable
fun ConverterTopBar(
    navController : NavController
) {
    TopAppBar(
        navigationIcon = { NavigationIconButton(navController = navController) },
        title = { },
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

@Composable
fun ConverterContent(
    rates : List<CurrencyRates>,
    networkError : Boolean,
    inputError : Boolean,
    value : String,
    setValue : (String) -> Unit
) {
    Column() {

        if (!rates.isNullOrEmpty()) {

            BaseCurrencyCard(rates[0].baseCurrencyAcronym, rates[0].date, value, inputError, setValue)

            if (networkError) {
                if (rates.isNullOrEmpty()) {
                    Text(text = "NETWORK ERROR")
                } else {
                    CurrencyRatesList(rates)
                }
            } else {
                CurrencyRatesList(rates)
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


@Composable
fun BaseCurrencyCard(
    baseCurrencyAcronym : String,
    date : String,
    value : String,
    inputError : Boolean,
    setValue : (String) -> Unit
) {

//    val value = remember () {
//        mutableStateOf(value)
//    }


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
                    text = "Currency rates by $date",
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
                        value = value.toString(),
                        onValueChange = { setValue(it) },
                        isError = inputError,
                        label = { Text(text = "VALUE")},
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done,
                            capitalization = KeyboardCapitalization.Sentences,
                            keyboardType = KeyboardType.Number
                        ),
//                        colors = TextFieldDefaults.textFieldColors(
//                            backgroundColor = Color.White,
//                            cursorColor = Color.Black,
//                            focusedIndicatorColor = Color.White,
//                            unfocusedIndicatorColor = Color.White,
//                            disabledIndicatorColor = Color.White,
//                            errorIndicatorColor = Color.White,
//                            focusedLabelColor = MaterialTheme.colors.onSurface.copy(ContentAlpha.medium),
//                            unfocusedLabelColor = MaterialTheme.colors.onSurface.copy(ContentAlpha.medium),
//                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
//                                focusManager.clearFocus()
//                                keyboardController?.hide()
                            }
                        ),
                    )
                }
            }
        }

    }

}