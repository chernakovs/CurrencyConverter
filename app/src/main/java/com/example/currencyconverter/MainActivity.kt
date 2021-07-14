package com.example.currencyconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.currencyconverter.ui.screens.ConverterScreen
import com.example.currencyconverter.ui.screens.CurrencyListScreen
import com.example.currencyconverter.ui.theme.CurrencyConverterTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CurrencyConverterTheme {

                val systemUiController = rememberSystemUiController()
                SideEffect {
                    systemUiController.setSystemBarsColor(
                        color = Color.White,
                    )
                }

                CurrencyConverterApp()
            }
        }
    }
}

sealed class Screen(val route: String) {
    object CurrencyList: Screen("currencyList")
    object Converter: Screen("converter")
}

@Composable
fun CurrencyConverterApp() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = Screen.CurrencyList.route) {
        composable(route = Screen.CurrencyList.route) {
            CurrencyListScreen(navController)
        }
        composable(route = Screen.Converter.route) {
            ConverterScreen(navController)
        }
    }

}