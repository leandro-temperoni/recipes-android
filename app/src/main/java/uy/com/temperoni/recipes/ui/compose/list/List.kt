package uy.com.temperoni.recipes.ui.compose.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import uy.com.temperoni.recipes.ui.activities.goToDetail
import uy.com.temperoni.recipes.ui.model.Recipe
import uy.com.temperoni.recipes.ui.theme.Shapes

@ExperimentalPagerApi
@Composable
fun List(recipes: List<Recipe>) {
    LazyColumn(
        Modifier.padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(recipes) { recipe ->
            RecipeRow(recipe)
        }
    }
}

@ExperimentalPagerApi
@Composable
fun RecipeRow(recipe: Recipe) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .height(300.dp)
            .padding(0.dp, 6.dp)
            .clickable { goToDetail(context, recipe.id, recipe.name) }, shape = Shapes.medium
    ) {
        Image(
            painter = rememberImagePainter(
                data = recipe.images.getOrNull(0),
                builder = {
                    crossfade(true)
                }
            ),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth(1f)
        )
        Box(modifier = Modifier.fillMaxWidth(1f), contentAlignment = Alignment.BottomStart) {
            Text(
                color = Color.White,
                text = recipe.name,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}