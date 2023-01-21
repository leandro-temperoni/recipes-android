package uy.com.temperoni.recipes.ui.compose.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import uy.com.temperoni.recipes.ui.model.Ingredient

@Composable
fun Ingredient(data: Ingredient) {
    Row(
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(12.dp), verticalAlignment = Alignment.Top
    ) {
        Text(
            color = MaterialTheme.colors.onSurface,
            text = data.name,
            modifier = Modifier.weight(.6f),
            style = MaterialTheme.typography.body1,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            color = MaterialTheme.colors.primary,
            text = data.amount,
            modifier = Modifier.weight(.4f),
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.body2,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun Ingredients(ingredients: List<Ingredient>) {
    Column {
        ingredients.forEach { ingredient ->
            Ingredient(data = ingredient)
        }
    }
}