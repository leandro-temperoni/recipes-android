package uy.com.temperoni.recipes.ui.compose.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.PagerState
import uy.com.temperoni.recipes.R
import uy.com.temperoni.recipes.ui.model.Ingredient
import uy.com.temperoni.recipes.ui.model.Recipe

@ExperimentalPagerApi
@Composable
fun DetailImage(urls: List<String>) {
    val pagerState = remember {
        PagerState()
    }
    Box(modifier = Modifier.fillMaxWidth(1f)) {
        HorizontalPager(count = urls.size, state = pagerState) { index ->
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = rememberImagePainter(
                        data = urls[index],
                        builder = {
                            crossfade(true)
                        }
                    ),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .height(400.dp)
                )
            }
        }
    }

    if (urls.size > 1) {
        Box(modifier = Modifier.fillMaxWidth(1f)) {
            HorizontalPagerIndicator(
                pagerState = pagerState,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(16.dp),
                pageCount = urls.size,
                activeColor = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun DetailSummary(name: String, description: String) {
    Text(
        color = MaterialTheme.colorScheme.onSurface,
        text = name,
        modifier = Modifier.padding(8.dp),
        fontSize = 32.sp
    )

    SubTitle("DESCRIPCION")

    Text(
        color = MaterialTheme.colorScheme.onSurface,
        text = description,
        modifier = Modifier.padding(8.dp),
        style = MaterialTheme.typography.bodyLarge
    )
}

@Composable
fun DetailTabs(recipe: Recipe) {
    Section(ingredients = recipe.ingredients)
    Steps(instructions = recipe.instructions)
}

@Composable
fun SubTitle(text: String) {
    Text(
        color = MaterialTheme.colorScheme.primary,
        text = text.toUpperCase(Locale.current),
        modifier = Modifier.padding(8.dp),
        style = MaterialTheme.typography.titleMedium
    )
}

@Composable
fun Section(ingredients: List<Ingredient>) {
    Surface(
        color = MaterialTheme.colorScheme.onSecondary,
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier.padding(8.dp)
    ) {
        Ingredients(ingredients)
    }
}

private enum class DetailsTabs(val text: String, val icon: Int) {
    Ingredients("INGREDIENTES", R.drawable.ic_baseline_kitchen_24),
    Steps("PASOS", R.drawable.ic_baseline_format_list_numbered_24)
}