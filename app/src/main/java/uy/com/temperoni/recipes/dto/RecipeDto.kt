package uy.com.temperoni.recipes.dto

/**
 * @author Leandro Temperoni
 */
data class RecipeDto(var id: Int? = 0,
                     var image: String? = "",
                     var ingredients: List<IngredientDto?>? = null,
                     var introduction: String? = "",
                     var name: String? = null,
                     var instructions: List<InstructionDto?>? = null)
