package uy.com.temperoni.recipes.viewmodel

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.*
import org.junit.Test
import org.mockito.Mockito.*
import uy.com.temperoni.recipes.commons.BaseViewModelTest
import uy.com.temperoni.recipes.commons.mockRecipe
import uy.com.temperoni.recipes.domain.GetRecipesBookUseCase
import uy.com.temperoni.recipes.dto.RecipeDto
import uy.com.temperoni.recipes.mappers.RecipesMapper
import uy.com.temperoni.recipes.repository.RecipesRepository

@OptIn(ExperimentalCoroutinesApi::class)
class RecipesViewModelTest : BaseViewModelTest() {

    @Test
    fun givenMockFlow_whenCallingGetRecipes_thenShouldCallFetchRecipesAndReturnResult() = runTest {
        // Arrange
        val list = listOf(RecipeDto("1"), RecipeDto("2"))
        val mappedList = listOf(mockRecipe("1"), mockRecipe("2"))
        val mockFlow: Flow<List<RecipeDto>> = flow { emit(list) }
        val repo: RecipesRepository = mock(RecipesRepository::class.java)
        `when`(repo.fetchRecipes()).thenReturn(mockFlow)
        val mapper: RecipesMapper = mock(RecipesMapper::class.java)
        `when`(mapper.mapRecipes(list)).thenReturn(mappedList)
        val useCase = mock(GetRecipesBookUseCase::class.java)
        val viewModel = RecipesViewModel(repo, mapper, mainDispatcherRule.testDispatcher, useCase)

        // Act
        viewModel.getRecipes()

        // Assert
        verify(repo).fetchRecipes()
        verify(useCase).invoke(mappedList)
    }

    @Test(expected = Exception::class)
    fun givenErrorMockFlow_whenCallingGetRecipes_thenShouldCallFetchRecipesAndInvokeEmpty() = runTest {
        // Arrange
        val repo: RecipesRepository = mock(RecipesRepository::class.java)
        `when`(repo.fetchRecipes()).thenThrow(Exception())
        val mapper: RecipesMapper = mock(RecipesMapper::class.java)
        val useCase = mock(GetRecipesBookUseCase::class.java)
        val viewModel = RecipesViewModel(repo, mapper, mainDispatcherRule.testDispatcher, useCase)

        // Act
        viewModel.getRecipes()

        // Assert
        verify(repo).fetchRecipes()
        verify(useCase).invoke()
        assert(!viewModel.isLoading())
    }
}