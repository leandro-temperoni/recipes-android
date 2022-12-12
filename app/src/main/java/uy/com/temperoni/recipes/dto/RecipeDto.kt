package uy.com.temperoni.recipes.dto

/**
 * @author Leandro Temperoni
 */
data class RecipeDto(var id: String? = "",
                     var images: List<String?>? = null,
                     var ingredients: List<IngredientDto?>? = null,
                     var introduction: String? = "",
                     var name: String? = null,
                     var instructions: List<InstructionDto?>? = null)
