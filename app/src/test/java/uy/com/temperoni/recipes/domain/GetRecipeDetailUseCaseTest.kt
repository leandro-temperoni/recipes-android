package uy.com.temperoni.recipes.domain

import org.junit.Test
import uy.com.temperoni.recipes.commons.mockRecipe
import uy.com.temperoni.recipes.ui.state.ScreenState

class GetRecipeDetailUseCaseTest {

    @Test
    fun whenCallingInvoke_withNoResponse_shouldReturnErrorState() {
        // Arrange
        val useCase = GetRecipeDetailUseCase()

        // Act
        val result = useCase()

        // Assert
        assert(result.state == ScreenState.ERROR)
    }

    @Test
    fun whenCallingInvoke_withAValidResponse_shouldReturnSuccessState() {
        // Arrange
        val response = mockRecipe("1")
        val useCase = GetRecipeDetailUseCase()

        // Act
        val result = useCase(response)

        // Assert
        assert(result.state == ScreenState.SUCCESS)
        assert(result.item == response)
    }
}