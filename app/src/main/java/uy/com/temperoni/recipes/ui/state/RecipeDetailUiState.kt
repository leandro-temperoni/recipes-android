package uy.com.temperoni.recipes.ui.state

import uy.com.temperoni.recipes.ui.model.Recipe

class RecipeDetailUiState {

    lateinit var item: Recipe
    var state: ScreenState = ScreenState.LOADING
}