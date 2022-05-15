package uy.com.temperoni.recipes.ui.model

/**
 * @author Leandro Temperoni
 */
data class Recipe(val id: Int,
                  val images: List<String>,
                  val ingredients: List<Ingredient>,
                  val introduction: String,
                  val name: String,
                  val instructions: List<Instruction>)
