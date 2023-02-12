package uy.com.temperoni.recipes.viewmodel

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.chrisbanes.snapper.rememberSnapperFlingBehavior
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import uy.com.temperoni.recipes.domain.GetRecipesBookUseCase
import uy.com.temperoni.recipes.mappers.RecipesMapper
import uy.com.temperoni.recipes.repository.RecipesRepository
import uy.com.temperoni.recipes.ui.model.Grocery
import uy.com.temperoni.recipes.ui.state.RecipesUiState
import uy.com.temperoni.recipes.ui.state.ScreenState
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    private val repository: RecipesRepository,
    private val mapper: RecipesMapper,
    private val getRecipesBookUseCase: GetRecipesBookUseCase
) : ViewModel() {

    private var recipesBookStateRecipes: MutableStateFlow<RecipesUiState>? = null

    private val _groceries = getMockGroceries().toMutableStateList()
    val groceries: List<Grocery>
        get() = _groceries

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

    private fun getMockGroceries(): MutableList<Grocery> {
        return mutableListOf(Grocery("1", "Manteca"), Grocery("2", "Azucar"), Grocery("3", "Leche"), Grocery("4", "Cacao"))
    }

    fun updateGrocery(grocery: Grocery, isChecked: Boolean) {
        grocery.checked.value = isChecked
    }
    fun removeGrocery(grocery: Grocery) {
        _groceries.remove(grocery)
    }
}