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
                desserts = response.filter { it.id <= 11 }
                hasDesserts = desserts.isNotEmpty()
                preparations = response.filter { it.id > 11 }
                hasPreparations = preparations.isNotEmpty()

                state = ScreenState.SUCCESS_LIST
            }
        }
    }
}