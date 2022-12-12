package uy.com.temperoni.recipes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import uy.com.temperoni.recipes.mappers.RecipesMapper
import uy.com.temperoni.recipes.repository.RecipesRepository
import uy.com.temperoni.recipes.ui.state.RecipeDetailUiState
import uy.com.temperoni.recipes.ui.state.ScreenState
import javax.inject.Inject

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    private val repository: RecipesRepository,
    private val mapper: RecipesMapper
) : ViewModel() {

    private var recipeDetailState: MutableStateFlow<RecipeDetailUiState>? = null

    fun getRecipe(id: String): MutableStateFlow<RecipeDetailUiState> {
        if (recipeDetailState == null) {
            loadRecipe(id)
            recipeDetailState = MutableStateFlow(RecipeDetailUiState())
        }
        return recipeDetailState!!
    }

    private fun loadRecipe(id: String) {
        viewModelScope.launch {
            repository.fetchRecipe(id)
                .catch {
                    recipeDetailState!!.value = RecipeDetailUiState().apply {
                        state = ScreenState.ERROR
                    }
                }
                .map { response ->
                    mapper.mapRecipe(response)
                }
                .collect { response ->
                    recipeDetailState!!.value = RecipeDetailUiState().apply {
                        item = response
                        state = ScreenState.DETAIL
                    }
                }
        }
    }
}