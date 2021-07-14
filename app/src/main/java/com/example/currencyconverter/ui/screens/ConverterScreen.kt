package com.example.currencyconverter.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.currencyconverter.Screen

@Composable
fun ConverterScreen(
    navController : NavController
) {

    Scaffold(
        topBar = { ConverterTopBar(navController = navController) },
        content = { ConverterContent() }
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
            imageVector = Icons.Outlined.KeyboardArrowLeft,
            contentDescription = "back button",
            tint = Color.Black
        )
    }
}

@Composable
fun ConverterContent() {

}