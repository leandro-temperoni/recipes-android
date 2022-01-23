package uy.com.temperoni.recipes.ui.state

import uy.com.temperoni.recipes.ui.model.Recipe

class RecipesUiState {

    var items: List<Recipe> = listOf()
    var state: ScreenState = ScreenState.LOADING
}