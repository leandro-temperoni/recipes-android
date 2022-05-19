package uy.com.temperoni.recipes.ui.state

import uy.com.temperoni.recipes.ui.model.Recipe

class RecipesUiState {

    var desserts: List<Recipe> = listOf()
    var preparations: List<Recipe> = listOf()

    fun hasDesserts() = desserts.isNotEmpty()
    fun hasPreparations() = preparations.isNotEmpty()

    var state: ScreenState = ScreenState.LOADING
}