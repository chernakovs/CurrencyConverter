package com.example.currencyconverter.ui.screens

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Settings
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.currencyconverter.Screen
import com.example.currencyconverter.currencies.CurrencyListViewModel
import com.example.currencyconverter.data.Currency

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun CurrencyListScreen(
    navController : NavController,
    viewModel : CurrencyListViewModel
) {

    val currenciesList = viewModel.currencies.collectAsState(initial = listOf())
    val searchQuery = viewModel.searchQuery.collectAsState()
    val setSearchQuery = viewModel::setSearchQuery
    val networkError = viewModel.networkError.collectAsState()

    Scaffold(
        topBar = { CurrencyListTopBar() },
        content = {
            CurrencyListContent(
                currenciesList.value,
                networkError.value,
                searchQuery.value,
                setSearchQuery,
                navController
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

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun CurrencyListContent(
    currenciesList : List<Currency>,
    networkError: Boolean,
    searchQuery: String,
    setSearchQuery: (String) -> Unit,
    navController : NavController,
    ) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        SearchRow(searchQuery = searchQuery, setSearchQuery = setSearchQuery)

        if (currenciesList.isNullOrEmpty()) {
            if (networkError) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "NETWORK ERROR")
                }
            }
        } else {
            CurrencyList(currenciesList, navController)
        }

    }
}


@ExperimentalMaterialApi
@Composable
fun CurrencyList(
    currenciesList : List<Currency>,
    navController : NavController,
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
            CurrencyCard(currency = currency, navController)
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun CurrencyCard(
    currency : Currency,
    navController : NavController,
) {

    Card(
        onClick = {
            navController.navigate(Screen.Converter.createRoute(currency.acronym))
        }
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


@ExperimentalComposeUiApi
@Composable
fun SearchRow(
    searchQuery : String,
    setSearchQuery : (String) -> Unit
) {

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    TextField(
        value = searchQuery,
        onValueChange = { setSearchQuery(it) },
        leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "search") },
        trailingIcon = {
            IconButton(
                onClick = { setSearchQuery("") }) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "search",
                    modifier = Modifier.size(18.dp)
                )
            }
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.White,
            cursorColor = Color.Black,
            focusedIndicatorColor = Color.White,
            unfocusedIndicatorColor = Color.White,
            disabledIndicatorColor = Color.White,
            errorIndicatorColor = Color.White,
            focusedLabelColor = MaterialTheme.colors.onSurface.copy(ContentAlpha.medium),
            unfocusedLabelColor = MaterialTheme.colors.onSurface.copy(ContentAlpha.medium),
        ),
        shape = RoundedCornerShape(24.dp),
        maxLines = 2,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search,
            capitalization = KeyboardCapitalization.Sentences,
            keyboardType = KeyboardType.Text
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                focusManager.clearFocus()
                keyboardController?.hide()
            }
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
            .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(24.dp))
    )
}