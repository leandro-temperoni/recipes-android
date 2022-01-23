package uy.com.temperoni.recipes.viewmodel

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.mockito.Mockito.*
import uy.com.temperoni.recipes.dto.RecipeDto
import uy.com.temperoni.recipes.mappers.RecipesMapper
import uy.com.temperoni.recipes.repository.RecipesRepository
import uy.com.temperoni.recipes.ui.model.Recipe
import uy.com.temperoni.recipes.ui.state.ScreenState

@ExperimentalCoroutinesApi
class RecipesViewModelTest {

    // TODO migrate this
    private val testDispatcher = TestCoroutineDispatcher()

    @Test
    fun givenMockFlow_whenCallingGetRecipes_thenShouldCallFetchRecipesAndReturnResult() = testDispatcher.runBlockingTest {
        // Arrange
        val list = listOf(RecipeDto(1), RecipeDto(2))
        val mockFlow: Flow<List<RecipeDto>> = flow { emit(list) }
        val repo: RecipesRepository = mock(RecipesRepository::class.java)
        `when`(repo.fetchRecipes()).thenReturn(mockFlow)
        val mapper: RecipesMapper = mock(RecipesMapper::class.java)
        `when`(mapper.mapRecipes(list)).thenReturn(listOf(mockRecipe(1), mockRecipe(2)))
        val viewModel = RecipesViewModel(repo, mapper, testDispatcher)

        // Act
        viewModel.getRecipes()

        // Assert
        verify(repo).fetchRecipes()
        assert(ScreenState.LIST == viewModel.getRecipes().value.state)
        assert(list.size == viewModel.getRecipes().value.items.size)
    }

    private fun mockRecipe(id: Int) = Recipe(
        id = id,
        image = "",
        introduction = "",
        name = "name",
        ingredients = emptyList(),
        instructions = emptyList()
    )
}