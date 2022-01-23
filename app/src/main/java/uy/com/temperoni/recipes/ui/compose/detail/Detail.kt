package uy.com.temperoni.recipes.ui.compose.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import uy.com.temperoni.recipes.R
import uy.com.temperoni.recipes.ui.model.Recipe

@Composable
fun DetailImage(url: String) {
    Image(
        painter = rememberImagePainter(
            data = url,
            builder = {
                crossfade(true)
            }
        ),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth(1f)
            .height(350.dp)
    )
}

@Composable
fun DetailSummary(name: String, description: String) {
    Text(
        color = MaterialTheme.colors.onSurface,
        text = name,
        modifier = Modifier.padding(8.dp),
        fontSize = 32.sp
    )

    SubTitle("DESCRIPCION")

    Text(
        color = MaterialTheme.colors.onSurface,
        text = description,
        modifier = Modifier.padding(8.dp),
        style = MaterialTheme.typography.body1
    )
}

@Composable
fun DetailTabs(recipe: Recipe) {
    var state by remember { mutableStateOf(0) }
    TabRow(selectedTabIndex = state) {
        DetailsTabs.values().forEachIndexed { index, tab ->
            Tab(
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = tab.icon),
                        contentDescription = ""
                    )
                },
                text = { Text(tab.text) },
                selected = state == index,
                onClick = { state = index }
            )
        }
    }

    when (state) {
        0 -> Ingredients(ingredients = recipe.ingredients)
        1 -> Steps(instructions = recipe.instructions)
        else -> {}
    }
}

@Composable
fun SubTitle(text: String) {
    Text(
        color = MaterialTheme.colors.primary,
        text = text.toUpperCase(Locale.current),
        modifier = Modifier.padding(8.dp),
        style = MaterialTheme.typography.subtitle1
    )
}

private enum class DetailsTabs(val text: String, val icon: Int) {
    Ingredients("INGREDIENTES", R.drawable.ic_baseline_kitchen_24),
    Steps("PASOS", R.drawable.ic_baseline_format_list_numbered_24)
}