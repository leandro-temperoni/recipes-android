package uy.com.temperoni.recipes.ui.activities

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.accompanist.pager.ExperimentalPagerApi
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import uy.com.temperoni.recipes.ui.compose.list.List
import uy.com.temperoni.recipes.ui.model.Recipe
import uy.com.temperoni.recipes.ui.state.RecipesUiState
import uy.com.temperoni.recipes.ui.state.ScreenState
import uy.com.temperoni.recipes.ui.theme.RecetasTheme

@ExperimentalPagerApi
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun givenTwoItems_whenDisplayingList_thenShouldShowTwoTitles() {
        // Arrange
        val list = listOf(mockRecipe(1, name = "Receta 1"), mockRecipe(2, name = "Receta 2"))

        // Act
        composeTestRule.setContent {
            RecetasTheme {
                List(list, true)
            }
        }

        // Assert
        composeTestRule.onNodeWithText("Receta 1").assertExists()
        composeTestRule.onNodeWithText("Receta 2").assertExists()
        composeTestRule.onNodeWithText("Receta 3").assertDoesNotExist()
    }

    @Test
    fun givenNoItems_whenDisplayingList_thenShouldShowNoTitles() {
        // Arrange
        val list = emptyList<Recipe>()

        // Act
        composeTestRule.setContent {
            RecetasTheme {
                List(list, false)
            }
        }

        // Assert
        composeTestRule.onNodeWithText("Ocurrió un error al cargar el recetario").assertExists()
        composeTestRule.onNodeWithText("Receta 1").assertDoesNotExist()
        composeTestRule.onNodeWithText("Receta 2").assertDoesNotExist()
        composeTestRule.onNodeWithText("Receta 3").assertDoesNotExist()
    }

    private fun mockRecipe(id: Int, name: String) = Recipe(
        id = id,
        images = listOf(""),
        introduction = "",
        name = name,
        ingredients = emptyList(),
        instructions = emptyList()
    )
}