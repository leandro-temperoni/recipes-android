package uy.com.temperoni.recipes.ui.activities

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import uy.com.temperoni.recipes.dto.Recipe
import uy.com.temperoni.recipes.ui.compose.list.List
import uy.com.temperoni.recipes.ui.state.RecipesUiState
import uy.com.temperoni.recipes.ui.state.ScreenState
import uy.com.temperoni.recipes.ui.theme.RecetasTheme

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun givenTwoItems_whenDisplayingList_thenShouldShowTwoTitles() {
        // Arrange
        val list = listOf(Recipe(1, name = "Receta 1"), Recipe(2, name = "Receta 2"))
        val state = RecipesUiState().apply {
            state = ScreenState.LIST
            items = list
        }

        // Act
        composeTestRule.setContent {
            RecetasTheme {
                List(state)
            }
        }

        // Assert
        composeTestRule.onNodeWithText("Receta 1").assertExists()
        composeTestRule.onNodeWithText("Receta 2").assertExists()
        composeTestRule.onNodeWithText("Receta 3").assertDoesNotExist()
    }
}