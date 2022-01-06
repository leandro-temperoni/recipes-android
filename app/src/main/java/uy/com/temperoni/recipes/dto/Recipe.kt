package uy.com.temperoni.recipes.dto

/**
 * @author Leandro Temperoni
 */
data class Recipe(var id: Int? = 0,
                  var image: String? = "",
                  var ingredients: List<Ingredient?>? = null,
                  var introduction: String? = "",
                  var name: String? = null,
                  var instructions: List<InstructionItem?>? = null)
