package uy.com.temperoni.recipes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import uy.com.temperoni.recipes.domain.GetRecipesBookUseCase
import uy.com.temperoni.recipes.mappers.RecipesMapper
import uy.com.temperoni.recipes.repository.RecipesRepository
import uy.com.temperoni.recipes.ui.state.RecipesUiState
import uy.com.temperoni.recipes.ui.state.ScreenState
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    private val repository: RecipesRepository,
    private val mapper: RecipesMapper,
    private val dispatcher: CoroutineDispatcher,
    private val getRecipesBookUseCase: GetRecipesBookUseCase
) : ViewModel() {

    private var recipesBookStateRecipes: MutableStateFlow<RecipesUiState>? = null

    init {
        getRecipes()
    }

    fun getRecipes(): MutableStateFlow<RecipesUiState> {
        if (recipesBookStateRecipes == null) {
            recipesBookStateRecipes = MutableStateFlow(RecipesUiState())
            loadRecipes()
        }
        return recipesBookStateRecipes!!
    }

    fun isLoading(): Boolean {
        return recipesBookStateRecipes!!.value.state == ScreenState.LOADING
    }

    private fun loadRecipes() {
        viewModelScope.launch(Dispatchers.Main) {
            repository.fetchRecipes()
                .catch {
                    recipesBookStateRecipes!!.value = getRecipesBookUseCase()
                }
                .map { response ->
                    mapper.mapRecipes(response)
                }
                .collect { response ->
                    recipesBookStateRecipes!!.value = getRecipesBookUseCase(response)
                }
        }
    }
}