package uy.com.temperoni.recipes.ui.compose.commons

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import uy.com.temperoni.recipes.R

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

@Composable
fun GenericMessage(message: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth(1f)
            .fillMaxHeight(1f), contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                modifier = Modifier.size(40.dp),
                imageVector = ImageVector.vectorResource(R.drawable.ic_baseline_error_24),
                contentDescription = "",
                colorFilter = ColorFilter.tint(
                    MaterialTheme.colorScheme.onSurface
                )
            )
            Text(modifier = Modifier.padding(0.dp, 8.dp, 0.dp, 0.dp), text = message)
        }
    }
}