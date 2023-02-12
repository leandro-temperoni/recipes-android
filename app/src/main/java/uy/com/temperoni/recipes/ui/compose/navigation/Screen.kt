package uy.com.temperoni.recipes.ui.compose.navigation

import uy.com.temperoni.recipes.R

sealed class Screen(val route: String, val icon: Int) {
    object Desserts : Screen("Recetas", R.drawable.ic_baseline_restaurant_24)
    object Groceries : Screen("Compras", R.drawable.ic_baseline_local_grocery_store_24)
    object Chronometer : Screen("Cronometro", R.drawable.ic_baseline_timer_24)
}
