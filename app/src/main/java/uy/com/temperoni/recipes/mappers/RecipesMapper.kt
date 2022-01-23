package uy.com.temperoni.recipes.mappers

import uy.com.temperoni.recipes.dto.IngredientDto
import uy.com.temperoni.recipes.dto.InstructionDto
import uy.com.temperoni.recipes.dto.RecipeDto
import uy.com.temperoni.recipes.ui.model.Ingredient
import uy.com.temperoni.recipes.ui.model.Instruction
import uy.com.temperoni.recipes.ui.model.Recipe
import javax.inject.Inject

class RecipesMapper @Inject constructor() {

    fun mapRecipes(data: List<RecipeDto>): List<Recipe> {
        val list: MutableList<Recipe> = mutableListOf()
        data.filter { item ->
            item.id != null
        }.forEach { recipeDto ->
            list.add(mapRecipe(recipeDto))
        }

        return list
    }

    fun mapRecipe(data: RecipeDto): Recipe {
        return Recipe(
            data.id ?: -1,
            data.image ?: "",
            mapIngredient(data.ingredients ?: emptyList()),
            data.introduction ?: "",
            data.name ?: "",
            mapInstruction(data.instructions ?: emptyList())
        )
    }

    private fun mapIngredient(data: List<IngredientDto?>): List<Ingredient> {
        val list: MutableList<Ingredient> = mutableListOf()
        data.filterNotNull().forEach { ingredientDto ->
            list.add(Ingredient(
                ingredientDto.amount ?: "",
                ingredientDto.name ?: ""
            ))
        }

        return list
    }

    private fun mapInstruction(data: List<InstructionDto?>): List<Instruction> {
        val list: MutableList<Instruction> = mutableListOf()
        data.filterNotNull().forEach { instructionDto ->
            list.add(Instruction(
                instructionDto.description ?: "",
                instructionDto.steps?.filterNotNull() ?: emptyList()
            ))
        }

        return list
    }
}