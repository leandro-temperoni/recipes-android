package uy.com.temperoni.recipes.ui.compose.groceries

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import uy.com.temperoni.recipes.ui.model.Grocery
import uy.com.temperoni.recipes.viewmodel.RecipesViewModel

@Composable
fun Groceries(viewModel: RecipesViewModel) {
    val data = viewModel.getGroceries().collectAsState()
    LazyColumn(
        Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(data.value) { item ->
            GroceryItem(item) { grocery, newValue -> viewModel.updateGrocery(grocery, newValue) }
        }
    }
}

@Composable
fun GroceryItem(grocery: Grocery, onCheck: (Grocery, Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth(1f)
            .background(
                color = MaterialTheme.colorScheme.tertiaryContainer,
                shape = MaterialTheme.shapes.small,
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = grocery.checked.value,
            onCheckedChange = { newValue -> onCheck(grocery, newValue) })
        Text(
            text = grocery.name,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(8.dp, 32.dp),
            textDecoration = if (grocery.checked.value) TextDecoration.LineThrough else TextDecoration.None
        )
    }
}