package uy.com.temperoni.recipes.repository

import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import uy.com.temperoni.recipes.commons.BaseMockitoInjectTest
import uy.com.temperoni.recipes.dto.RecipeDto
import uy.com.temperoni.recipes.repository.RecipesRepository.Companion.API_URL
import uy.com.temperoni.recipes.repository.networking.RecipesApi
import java.lang.reflect.Type

@OptIn(ExperimentalCoroutinesApi::class)
class RecipesRepositoryTest : BaseMockitoInjectTest() {

    @InjectMocks
    private lateinit var repository: RecipesRepository

    @Mock
    private lateinit var api: RecipesApi

    @Test
    fun whenCallingFetchRecipes_shouldCallApiToGetRecipesList() = runTest {
        // Arrange
        val data = emptyList<RecipeDto>()
        val type: Type = object : TypeToken<List<RecipeDto>>() {}.type
        `when`(api.get<List<RecipeDto>>(API_URL, type)).thenReturn(data)

        // Act
        val result = repository.fetchRecipes().first()

        // Assert
        verify(api).get<List<RecipeDto>>(API_URL, type)
        assertEquals(data, result)
    }

    @Test
    fun givenAnId_whenCallingFetchRecipe_shouldCallApiToGetRecipeDetailWithThatSameId() = runTest {
        // Arrange
        val id = "1"
        val data = RecipeDto(id)
        `when`(api.get("$API_URL/$id", RecipeDto::class.java)).thenReturn(data)

        // Act
        val result = repository.fetchRecipe(id).first()

        // Assert
        verify(api).get("$API_URL/$id", RecipeDto::class.java)
        assertEquals(data, result)
    }
}