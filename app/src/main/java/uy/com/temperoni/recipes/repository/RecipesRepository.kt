package uy.com.temperoni.recipes.repository

import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uy.com.temperoni.recipes.dto.RecipeDto
import uy.com.temperoni.recipes.repository.networking.RecipesApi
import java.lang.reflect.Type
import javax.inject.Inject

class RecipesRepository @Inject constructor(private val api: RecipesApi) {

    companion object {
        const val API_URL = "https://2kxenzmhnf.execute-api.us-east-1.amazonaws.com/recipes"
    }

    suspend fun fetchRecipes(): Flow<List<RecipeDto>> = flow {
        val type: Type = object : TypeToken<List<RecipeDto>>() {}.type
        val data: List<RecipeDto> = api.get(API_URL, type)
        emit(data)
    }.flowOn(Dispatchers.IO)

    fun fetchRecipe(id: String): RecipeDto {
        return api.get("$API_URL/$id", RecipeDto::class.java)
    }
}
