package com.example.currencyconverter.ui.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.currencyconverter.CurrencyConverterApplication
import com.example.currencyconverter.ui.converter.CurrencyConverterViewModelFactory
import com.example.currencyconverter.ui.converter.screens.ConverterScreen
import com.example.currencyconverter.ui.currencies.CurrencyListViewModelFactory
import com.example.currencyconverter.ui.currencies.screens.CurrencyListScreen


sealed class Screen(val route: String) {
    object CurrencyList : Screen("currencyList")
    object Converter : Screen("{currencyAcronym}/converter") {
        fun createRoute(currencyAcronym: String) = "$currencyAcronym/converter"
    }
}


@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun CurrencyConverterAppNavigation(
    application: CurrencyConverterApplication
) {
    val navController = rememberNavController()
    val repository = application.currencyRepository

    NavHost(navController, startDestination = Screen.CurrencyList.route) {
        composable(route = Screen.CurrencyList.route) {

            CurrencyListScreen(
                navController,
                viewModel(factory = CurrencyListViewModelFactory(repository))
            )
        }
        composable(route = Screen.Converter.route) { backStackEntry ->
            val currencyAcronym = backStackEntry.arguments?.getString("currencyAcronym")
            requireNotNull(currencyAcronym) { "currencyAcronym not found" }
            ConverterScreen(
                navController,
                viewModel(
                    factory = CurrencyConverterViewModelFactory(
                        repository = repository,
                        currencyAcronym = currencyAcronym
                    )
                )
            )
        }
    }

}