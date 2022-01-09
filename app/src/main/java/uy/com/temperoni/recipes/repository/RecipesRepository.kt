package uy.com.temperoni.recipes.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uy.com.temperoni.recipes.dto.Recipe
import uy.com.temperoni.recipes.repository.RecipesApi.RecipeList
import javax.inject.Inject

class RecipesRepository @Inject constructor(private val api: RecipesApi) {

    fun fetchRecipes(): Flow<List<Recipe>> = flow {
        val data = api.getRecipesList("https://tempe-recipes-api.herokuapp.com/recipes")
        emit(data)
    }.flowOn(Dispatchers.IO)

    fun fetchRecipe(id: Int): Flow<Recipe> = flow {
        val data = api.getRecipeDetail("https://tempe-recipes-api.herokuapp.com/recipes/$id")
        emit(data)
    }.flowOn(Dispatchers.IO)
}
