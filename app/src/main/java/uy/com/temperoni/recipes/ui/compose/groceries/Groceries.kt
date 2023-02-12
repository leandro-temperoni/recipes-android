package uy.com.temperoni.recipes.ui.compose.groceries

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import uy.com.temperoni.recipes.ui.model.Grocery

@Composable
fun Groceries(
    groceries: List<Grocery>,
    onCheck: (Grocery, Boolean) -> Unit,
    onDelete: (Grocery) -> Unit
) {
    LazyColumn(
        Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(items = groceries, key = { item -> item.id }) { item ->
            GroceryItem(item,
                onCheck = { newValue -> onCheck(item, newValue) },
                onDelete = { onDelete(item) })
        }
    }
}

@Composable
fun GroceryItem(
    grocery: Grocery,
    onCheck: (Boolean) -> Unit,
    onDelete: () -> Unit
) {
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
            onCheckedChange = onCheck
        )
        Text(
            text = grocery.name,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .weight(1f)
                .padding(8.dp, 32.dp),
            textDecoration = if (grocery.checked.value) TextDecoration.LineThrough else TextDecoration.None
        )
        IconButton(onClick = onDelete) {
            Icon(Icons.Filled.Close, contentDescription = null)
        }
    }
}