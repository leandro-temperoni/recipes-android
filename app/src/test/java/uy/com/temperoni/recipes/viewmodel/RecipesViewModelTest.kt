package uy.com.temperoni.recipes.viewmodel

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.mockito.Mockito.*
import uy.com.temperoni.recipes.dto.Recipe
import uy.com.temperoni.recipes.repository.RecipesRepository
import uy.com.temperoni.recipes.ui.state.ScreenState

@ExperimentalCoroutinesApi
class RecipesViewModelTest {

    // TODO migrate this
    private val testDispatcher = TestCoroutineDispatcher()

    @Test
    fun givenMockFlow_whenCallingGetRecipes_thenShouldCallFetchRecipesAndReturnResult() = testDispatcher.runBlockingTest {
        // Arrange
        val list = listOf(Recipe(1), Recipe(2))
        val mockFlow: Flow<List<Recipe>> = flow { emit(list) }
        val repo: RecipesRepository = mock(RecipesRepository::class.java)
        `when`(repo.fetchRecipes()).thenReturn(mockFlow)
        val viewModel = RecipesViewModel(repo, testDispatcher)

        // Act
        viewModel.getRecipes()

        // Assert
        verify(repo).fetchRecipes()
        assert(ScreenState.LIST == viewModel.getRecipes().value.state)
        assert(list.size == viewModel.getRecipes().value.items.size)
    }
}