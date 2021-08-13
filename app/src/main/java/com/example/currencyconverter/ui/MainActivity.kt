package com.example.currencyconverter.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import com.example.currencyconverter.CurrencyConverterApplication
import com.example.currencyconverter.ui.navigation.CurrencyConverterAppNavigation
import com.example.currencyconverter.ui.theme.CurrencyConverterTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    @ExperimentalComposeUiApi
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {

        val application = requireNotNull(this).application as CurrencyConverterApplication

        super.onCreate(savedInstanceState)
        setContent {
            CurrencyConverterTheme {

                val systemUiController = rememberSystemUiController()
                SideEffect {
                    systemUiController.setSystemBarsColor(
                        color = Color.White,
                    )
                }

                CurrencyConverterAppNavigation(application.currencyRepository)

            }
        }
    }
}
