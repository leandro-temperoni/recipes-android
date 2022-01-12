package uy.com.temperoni.recipes.ui.compose.commons

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun Loading() {
    Box(
        modifier = Modifier
            .fillMaxWidth(1f)
            .fillMaxHeight(1f), contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}