package uy.com.temperoni.recipes.ui.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

class Grocery(
    val name: String,
    var checked: MutableState<Boolean> = mutableStateOf(false)
)