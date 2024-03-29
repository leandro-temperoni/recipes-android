package uy.com.temperoni.recipes.domain

import uy.com.temperoni.recipes.ui.model.Recipe
import uy.com.temperoni.recipes.ui.state.RecipesUiState
import uy.com.temperoni.recipes.ui.state.ScreenState
import javax.inject.Inject

class GetRecipesBookUseCase @Inject constructor() {

    operator fun invoke(response: List<Recipe>? = null): RecipesUiState {
        return if (response == null) {
            RecipesUiState().apply {
                state = ScreenState.ERROR
            }
        } else {
            RecipesUiState().apply {
                desserts = response

                state = if (desserts.isNotEmpty()){
                    ScreenState.SUCCESS
                } else {
                    ScreenState.ZRP
                }
            }
        }
    }
}