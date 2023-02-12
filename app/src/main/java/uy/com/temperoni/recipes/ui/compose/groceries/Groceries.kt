package uy.com.temperoni.recipes.ui.compose.groceries

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.modifier.modifierLocalProvider
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import uy.com.temperoni.recipes.ui.model.Grocery

@Composable
fun Groceries(
    groceries: List<Grocery>,
    onCheck: (Grocery, Boolean) -> Unit,
    onDelete: (Grocery) -> Unit,
    onSave: (Grocery, String) -> Unit,
    onAdd: () -> Unit
) {
    LazyColumn(
        Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(items = groceries, key = { item -> item.id }) { item ->
            GroceryItem(item,
                onCheck = { newValue -> onCheck(item, newValue) },
                onDelete = { onDelete(item) }
            ) { newValue -> onSave(item, newValue) }
        }
        item {
            Box(modifier = Modifier.fillMaxWidth(1f)) {
                Button(onClick = onAdd, modifier = Modifier.align(Alignment.Center)) {
                    Icon(
                        Icons.Filled.Add,
                        contentDescription = "Agregar",
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text("Agregar")
                }
            }
        }
    }
}

@Composable
fun GroceryItem(
    grocery: Grocery,
    onCheck: (Boolean) -> Unit,
    onDelete: () -> Unit,
    onSave: (String) -> Unit
) {
    val focusRequester = remember {
        FocusRequester()
    }
    var text by remember { mutableStateOf(grocery.name) }
    var isEditing by remember {
        mutableStateOf(false)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth(1f)
            .background(
                color = MaterialTheme.colorScheme.tertiaryContainer,
                shape = MaterialTheme.shapes.small,
            )
            .clickable {
                if (!grocery.checked.value && !isEditing) {
                    isEditing = true
                }
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = grocery.checked.value,
            enabled = !isEditing,
            onCheckedChange = onCheck
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp, 32.dp)
        ) {
            if (isEditing) {
                BasicTextField(
                    modifier = Modifier.focusRequester(focusRequester),
                    value = text,
                    onValueChange = { text = it },
                    textStyle = MaterialTheme.typography.titleLarge
                )
                LaunchedEffect(key1 = "focus", block = {
                    focusRequester.requestFocus()
                })
            } else {
                Text(
                    text = grocery.name,
                    style = MaterialTheme.typography.titleLarge,
                    textDecoration = if (grocery.checked.value) TextDecoration.LineThrough else TextDecoration.None
                )
            }
        }
        if (isEditing) {
            IconButton(onClick = {
                isEditing = false
                onSave(text)
            }) {
                Icon(Icons.Filled.Check, contentDescription = null)
            }
        } else {
            IconButton(onClick = onDelete) {
                Icon(Icons.Filled.Close, contentDescription = null)
            }
        }
    }
}