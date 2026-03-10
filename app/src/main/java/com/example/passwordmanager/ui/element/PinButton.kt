package com.example.passwordmanager.ui.element

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PinButton(text: String, onClick: () -> Unit) {
    val isOkAction = text == "OK"

    Surface(
        onClick = onClick,
        shape = CircleShape,
        // Если это OK, красим в основной цвет приложения
        color = if (isOkAction) MaterialTheme.colorScheme.primary
        else MaterialTheme.colorScheme.surfaceVariant,
        contentColor = if (isOkAction) MaterialTheme.colorScheme.onPrimary
        else MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.size(80.dp)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = text,
                style = if (isOkAction) MaterialTheme.typography.titleMedium
                else MaterialTheme.typography.headlineSmall
            )
        }
    }
}