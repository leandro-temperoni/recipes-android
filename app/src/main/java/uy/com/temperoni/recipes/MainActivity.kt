package uy.com.temperoni.recipes

import android.content.res.Resources
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import uy.com.temperoni.recipes.dto.Recipe
import uy.com.temperoni.recipes.ui.theme.RecetasTheme
import uy.com.temperoni.recipes.ui.theme.Shapes
import uy.com.temperoni.recipes.viewmodel.RecipesViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: RecipesViewModel by viewModels()

        setContent {
            RecetasTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    RecipesList(viewModel, resources)
                }
            }
        }
    }
}

@Composable
fun RecipesList(viewModel: RecipesViewModel, resources: Resources) {
    val recipes: List<Recipe> by viewModel.getRecipes(resources).observeAsState(listOf())

    LazyColumn(Modifier.padding(8.dp, 4.dp)) {
        items(recipes) { recipe ->
            RecipeRow(recipe)
        }
    }
}

@Composable
fun RecipeRow(recipe: Recipe) {
    Surface(elevation = 4.dp, modifier = Modifier.height(200.dp).padding(0.dp, 4.dp), shape = Shapes.large) {
        Image(bitmap = ImageBitmap.imageResource(id = R.mipmap.foto), contentDescription = null, contentScale = ContentScale.Crop, modifier = Modifier
            .fillMaxWidth(1f))
        Box(modifier = Modifier.fillMaxWidth(1f), contentAlignment = Alignment.BottomStart) {
            Text(color = MaterialTheme.colors.background, text = recipe.name!!, modifier = Modifier.padding(8.dp))
        }
    }
}

//@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val list = arrayListOf(Recipe(name = "Budin de banana"))
    RecetasTheme {
        Surface(color = MaterialTheme.colors.background) {
            //RecipesList(list)
        }
    }
}