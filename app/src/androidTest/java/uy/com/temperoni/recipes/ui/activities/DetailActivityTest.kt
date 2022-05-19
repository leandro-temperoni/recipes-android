package uy.com.temperoni.recipes.ui.activities

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.accompanist.pager.ExperimentalPagerApi
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import uy.com.temperoni.recipes.ui.model.Ingredient
import uy.com.temperoni.recipes.ui.model.Instruction
import uy.com.temperoni.recipes.ui.model.Recipe
import uy.com.temperoni.recipes.ui.theme.RecetasTheme

@ExperimentalPagerApi
@RunWith(AndroidJUnit4::class)
class DetailActivityTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun givenMockRecipe_whenRenderingDetail_thenShouldShowCorrespondingInfo() {
        // Arrange
        val recipe = mockRecipe()

        // Act
        composeTestRule.setContent {
            RecetasTheme {
                Detail(recipe)
            }
        }

        // Assert
        composeTestRule.onNodeWithText("Receta 1").assertExists()
        composeTestRule.onNodeWithText("DESCRIPCION").assertExists()
        composeTestRule.onNodeWithText("Intro").assertExists()
        composeTestRule.onNodeWithText("INGREDIENTES").assertExists().assertHasClickAction()
        composeTestRule.onNodeWithText("PASOS").assertExists().assertHasClickAction()

        composeTestRule.onNodeWithText("300 gr").assertExists()
        composeTestRule.onNodeWithText("Azucar").assertExists()
        composeTestRule.onNodeWithText("2 tazas").assertExists()
        composeTestRule.onNodeWithText("Harina").assertExists()

        composeTestRule.onNodeWithText("Para la crema").assertDoesNotExist()
        for (i in 1..3) {
            composeTestRule.onNodeWithText("Paso Crema $i").assertDoesNotExist()
        }
        composeTestRule.onNodeWithText("Para la crema").assertDoesNotExist()
        for (i in 1..4) {
            composeTestRule.onNodeWithText("Paso $i").assertDoesNotExist()
        }
    }

    @Test
    fun givenMockRecipe_whenRenderingDetailAndTappingInstructions_thenShouldShowCorrespondingInfo() {
        // Arrange
        val recipe = mockRecipe()

        // Act
        composeTestRule.setContent {
            RecetasTheme {
                Detail(recipe)
            }
        }
        composeTestRule.onNodeWithText("PASOS").performClick()

        // Assert
        composeTestRule.onNodeWithText("Receta 1").assertExists()
        composeTestRule.onNodeWithText("DESCRIPCION").assertExists()
        composeTestRule.onNodeWithText("Intro").assertExists()
        composeTestRule.onNodeWithText("INGREDIENTES").assertExists().assertHasClickAction()
        composeTestRule.onNodeWithText("PASOS").assertExists().assertHasClickAction()

        composeTestRule.onNodeWithText("300 gr").assertDoesNotExist()
        composeTestRule.onNodeWithText("Azucar").assertDoesNotExist()
        composeTestRule.onNodeWithText("2 tazas").assertDoesNotExist()
        composeTestRule.onNodeWithText("Harina").assertDoesNotExist()

        composeTestRule.onNodeWithText("PARA LA CREMA").assertExists()
        for (i in 1..3) {
            composeTestRule.onNodeWithText("Paso Crema $i").assertExists()
        }
        composeTestRule.onNodeWithText("PARA LA BASE").assertExists()
        for (i in 1..4) {
            composeTestRule.onNodeWithText("Paso $i").assertExists()
        }

    }

    private fun mockRecipe() = Recipe(
        id = 1,
        images = listOf(""),
        introduction = "Intro",
        name = "Receta 1",
        ingredients = listOf(Ingredient("300 gr", "Azucar"), Ingredient("2 tazas", "Harina")),
        instructions = listOf(
            Instruction(
                "Para la crema",
                listOf("Paso Crema 1", "Paso Crema 2", "Paso Crema 3")
            ), Instruction("Para la base", listOf("Paso 1", "Paso 2", "Paso 3", "Paso 4"))
        )
    )
}