package uy.com.temperoni.recipes.ui.state

import uy.com.temperoni.recipes.ui.model.Recipe

class RecipesUiState {

    var desserts: List<Recipe> = listOf()

    var hasDesserts = false

    var state: ScreenState = ScreenState.LOADING
}