package uy.com.temperoni.recipes.ui.state

import uy.com.temperoni.recipes.dto.Recipe

class RecipeDetailUiState {

    var item: Recipe = Recipe()
    var state: ScreenState = ScreenState.LOADING
}