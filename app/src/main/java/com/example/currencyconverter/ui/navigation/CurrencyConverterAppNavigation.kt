package com.example.currencyconverter.ui.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.currencyconverter.domain.Repository
import com.example.currencyconverter.ui.ViewModelFactory
import com.example.currencyconverter.ui.converter.CurrencyConverterViewModel
import com.example.currencyconverter.ui.converter.screens.ConverterScreen
import com.example.currencyconverter.ui.currencies.CurrencyListViewModel
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
    repository: Repository
) {

    val navController = rememberNavController()

    NavHost(navController, startDestination = Screen.CurrencyList.route) {
        composable(route = Screen.CurrencyList.route) {

            val viewModel: CurrencyListViewModel = viewModel(factory = ViewModelFactory(repository))

            CurrencyListScreen(
                navController,
                viewModel
            )
        }
        composable(route = Screen.Converter.route) { backStackEntry ->
            val currencyAcronym = backStackEntry.arguments?.getString("currencyAcronym")
            requireNotNull(currencyAcronym) { "currencyAcronym not found" }
            val viewModel: CurrencyConverterViewModel = viewModel(factory = ViewModelFactory(repository))
            viewModel.setBaseCurrencyAndRates(currencyAcronym)
            ConverterScreen(
                navController,
                viewModel
            )
        }
    }

}