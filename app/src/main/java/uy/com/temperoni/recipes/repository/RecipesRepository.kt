package uy.com.temperoni.recipes.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uy.com.temperoni.recipes.dto.RecipeDto
import uy.com.temperoni.recipes.repository.networking.RecipesApi
import javax.inject.Inject

class RecipesRepository @Inject constructor(private val api: RecipesApi) {

    companion object {
        const val API_URL = "https://2kxenzmhnf.execute-api.us-east-1.amazonaws.com/recipes"
    }

    suspend fun fetchRecipes(): Flow<List<RecipeDto>> = flow {
        val data = api.get<List<RecipeDto>>(API_URL)
        emit(data)
    }.flowOn(Dispatchers.IO)

    suspend fun fetchRecipe(id: String): Flow<RecipeDto> = flow {
        val data = api.get<RecipeDto>("$API_URL/$id")
        emit(data)
    }.flowOn(Dispatchers.IO)
}
