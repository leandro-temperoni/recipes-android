package uy.com.temperoni.recipes.domain

import org.junit.Test
import uy.com.temperoni.recipes.commons.mockRecipe
import uy.com.temperoni.recipes.ui.state.ScreenState

class GetRecipesBookUseCaseTest {

    @Test
    fun whenCallingInvoke_withNoResponse_shouldReturnErrorState() {
        // Arrange
        val useCase = GetRecipesBookUseCase()

        // Act
        val result = useCase()

        // Assert
        assert(result.state == ScreenState.ERROR)
        assert(result.desserts.isEmpty())
    }

    @Test
    fun whenCallingInvoke_withAValidResponse_shouldReturnSuccessState() {
        // Arrange
        val response = listOf(mockRecipe("1"), mockRecipe("2"))
        val useCase = GetRecipesBookUseCase()

        // Act
        val result = useCase(response)

        // Assert
        assert(result.state == ScreenState.SUCCESS)
        assert(result.desserts.isNotEmpty())
    }
}