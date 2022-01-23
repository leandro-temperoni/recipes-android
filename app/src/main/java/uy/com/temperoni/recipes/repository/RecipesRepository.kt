package uy.com.temperoni.recipes.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uy.com.temperoni.recipes.dto.RecipeDto
import javax.inject.Inject

class RecipesRepository @Inject constructor(private val api: RecipesApi) {

    suspend fun fetchRecipes(): Flow<List<RecipeDto>> = flow {
        val data = api.getRecipesList()
        emit(data)
    }.flowOn(Dispatchers.IO)

    suspend fun fetchRecipe(id: Int): Flow<RecipeDto> = flow {
        val data = api.getRecipeDetail(id)
        emit(data)
    }.flowOn(Dispatchers.IO)
}
