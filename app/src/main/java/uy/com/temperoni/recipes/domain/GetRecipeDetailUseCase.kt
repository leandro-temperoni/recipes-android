package uy.com.temperoni.recipes.domain

import uy.com.temperoni.recipes.ui.model.Recipe
import uy.com.temperoni.recipes.ui.state.RecipeDetailUiState
import uy.com.temperoni.recipes.ui.state.ScreenState
import javax.inject.Inject

class GetRecipeDetailUseCase @Inject constructor() {

    operator fun invoke(response: Recipe? = null): RecipeDetailUiState {
        return if (response == null) {
            RecipeDetailUiState().apply {
                state = ScreenState.ERROR
            }
        } else {
            RecipeDetailUiState().apply {
                    item = response
                    state = ScreenState.SUCCESS
            }
        }
    }
}