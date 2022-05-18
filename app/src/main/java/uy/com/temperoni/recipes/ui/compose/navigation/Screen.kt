package uy.com.temperoni.recipes.ui.compose.navigation

sealed class Screen(val route: String) {
    object Desserts : Screen("Postres")
    object Preparations : Screen("Preparaciones")
    object Chronometer : Screen("Cronometro")
}
