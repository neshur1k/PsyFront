package com.example.angatkinmirea.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.angatkinmirea.domain.model.TodoItem

@Composable
fun TodoItemRow(
    item: TodoItem,
    isColorEnabled: Boolean,
    onCheckedChange: (Int) -> Unit,
    onClick: (Int) -> Unit
) {

    val backgroundColor =
        if (isColorEnabled && item.isCompleted) Color(0xFFB2FF59)
        else Color.Transparent

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onClick(item.id) }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = item.isCompleted,
            onCheckedChange = { onCheckedChange(item.id) }
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(text = item.title, style = MaterialTheme.typography.headlineMedium)
            Text(text = item.description, style = MaterialTheme.typography.bodyLarge)
        }
    }
}