package uy.com.temperoni.recipes.ui.compose.navigation

import uy.com.temperoni.recipes.R

sealed class Screen(val route: String, val icon: Int) {
    object Desserts : Screen("Postres", R.drawable.ic_baseline_restaurant_24)
    object Preparations : Screen("Preparaciones", R.drawable.ic_baseline_restaurant_menu_24)
    object Chronometer : Screen("Cronometro", R.drawable.ic_baseline_timer_24)
}
