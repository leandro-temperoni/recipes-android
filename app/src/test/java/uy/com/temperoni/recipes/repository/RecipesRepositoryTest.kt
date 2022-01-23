package uy.com.temperoni.recipes.repository

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import uy.com.temperoni.recipes.dto.RecipeDto

class RecipesRepositoryTest {

    @InjectMocks
    private lateinit var repository: RecipesRepository

    @Mock
    private lateinit var api: RecipesApi

    private lateinit var closeable: AutoCloseable

    @Before
    fun setUp() {
        closeable = MockitoAnnotations.openMocks(this)
    }

    @After
    fun tearDown() {
        closeable.close()
    }

    @Test
    fun whenCallingFetchRecipes_shouldCallApiToGetRecipesList(): Unit = runBlocking {
        // Arrange
        val data = emptyList<RecipeDto>()
        `when`(api.getRecipesList()).thenReturn(data)

        // Act
        val result = repository.fetchRecipes().first()

        // Assert
        verify(api).getRecipesList()
        assertEquals(data, result)
    }

    @Test
    fun givenAnId_whenCallingFetchRecipe_shouldCallApiToGetRecipeDetailWithThatSameId(): Unit = runBlocking {
        // Arrange
        val id = 3
        val data = RecipeDto(id)
        `when`(api.getRecipeDetail(id)).thenReturn(data)

        // Act
        val result = repository.fetchRecipe(id).first()

        // Assert
        verify(api).getRecipeDetail(id)
        assertEquals(data, result)
    }
}