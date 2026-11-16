package com.example.marketgo.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.marketgo.ui.theme.MarketGoTheme


@Composable
fun WelcomeScreen(
    onContinueClick: () -> Unit
) {

}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun WelcomeScreenPreview() {
    MarketGoTheme {
        WelcomeScreen(
            onContinueClick = {}
        )
    }
}