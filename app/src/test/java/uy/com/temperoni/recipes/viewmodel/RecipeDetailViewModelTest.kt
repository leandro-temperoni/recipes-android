package uy.com.temperoni.recipes.viewmodel

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito
import uy.com.temperoni.recipes.commons.BaseViewModelTest
import uy.com.temperoni.recipes.commons.mockRecipe
import uy.com.temperoni.recipes.domain.GetRecipeDetailUseCase
import uy.com.temperoni.recipes.dto.RecipeDto
import uy.com.temperoni.recipes.mappers.RecipesMapper
import uy.com.temperoni.recipes.repository.RecipesRepository

@ExperimentalCoroutinesApi
class RecipeDetailViewModelTest : BaseViewModelTest() {

    @Test
    fun givenMockFlow_whenCallingGetRecipeDetail_thenShouldCallFetchRecipeAndReturnResult() = runTest {
        // Arrange
        val recipe = RecipeDto("1")
        val mappedRecipe = mockRecipe("1")
        val repo: RecipesRepository = Mockito.mock(RecipesRepository::class.java)
        Mockito.`when`(repo.fetchRecipe("1")).thenReturn(recipe)
        val mapper: RecipesMapper = Mockito.mock(RecipesMapper::class.java)
        Mockito.`when`(mapper.mapRecipe(recipe)).thenReturn(mappedRecipe)
        val useCase = Mockito.mock(GetRecipeDetailUseCase::class.java)
        val viewModel = RecipeDetailViewModel(repo, mapper, mainDispatcherRule.testDispatcher, useCase)

        // Act
        viewModel.getRecipe("1")

        // Assert
        Mockito.verify(repo).fetchRecipe("1")
        Mockito.verify(useCase).invoke(mappedRecipe)
    }

    @Test(expected = Exception::class)
    fun givenErrorMockFlow_whenCallingGetRecipeDetail_thenShouldCallFetchRecipeAndInvokeEmpty() = runTest {
        // Arrange
        val repo: RecipesRepository = Mockito.mock(RecipesRepository::class.java)
        Mockito.`when`(repo.fetchRecipes()).thenThrow(Exception())
        val mapper: RecipesMapper = Mockito.mock(RecipesMapper::class.java)
        val useCase = Mockito.mock(GetRecipeDetailUseCase::class.java)
        val viewModel = RecipeDetailViewModel(repo, mapper, mainDispatcherRule.testDispatcher, useCase)

        // Act
        viewModel.getRecipe("2")

        // Assert
        Mockito.verify(repo).fetchRecipe("2")
        Mockito.verify(useCase).invoke()
    }
}