package com.example.currencyconverter.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.currencyconverter.ui.converter.CurrencyConverterViewModelFactory
import com.example.currencyconverter.ui.currencies.CurrencyListViewModelFactory
import com.example.currencyconverter.data.database.AppDatabase
import com.example.currencyconverter.data.database.AppDatabaseDao
import com.example.currencyconverter.ui.screens.ConverterScreen
import com.example.currencyconverter.ui.screens.CurrencyListScreen
import com.example.currencyconverter.ui.theme.CurrencyConverterTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    @ExperimentalComposeUiApi
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {

        val application = requireNotNull(this).application
        val dataSource = AppDatabase.getInstance(application).databaseDao

        super.onCreate(savedInstanceState)
        setContent {
            CurrencyConverterTheme {

                val systemUiController = rememberSystemUiController()
                SideEffect {
                    systemUiController.setSystemBarsColor(
                        color = Color.White,
                    )
                }

                CurrencyConverterApp(dataSource)
            }
        }
    }
}

sealed class Screen(val route: String) {
    object CurrencyList: Screen("currencyList")
    object Converter: Screen("{currencyAcronym}/converter") {
        fun createRoute(currencyAcronym: String) = "$currencyAcronym/converter"
    }
}

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun CurrencyConverterApp(
    dataSource : AppDatabaseDao
) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = Screen.CurrencyList.route) {
        composable(route = Screen.CurrencyList.route) {
            CurrencyListScreen(
                navController,
                viewModel(factory = CurrencyListViewModelFactory(dataSource))
            )
        }
        composable(route = Screen.Converter.route) { backStackEntry ->
            val currencyAcronym = backStackEntry.arguments?.getString("currencyAcronym")
            requireNotNull(currencyAcronym) { "currencyAcronym not found" }
            ConverterScreen(
                navController,
                viewModel(factory = CurrencyConverterViewModelFactory(dataSource = dataSource,
                    currencyAcronym = currencyAcronym
                ))
            )
        }
    }

}