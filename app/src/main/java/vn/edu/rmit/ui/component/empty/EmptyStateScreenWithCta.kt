package vn.edu.rmit.ui.component.empty

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddHome
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun EmptyStateScreenWithCta(
    icon: ImageVector,
    title: String,
    description: String,
    onActionClick: () -> Unit = {},  // Added parameter for button click
    actionIcon: ImageVector? = null,  // Optional icon for button
    actionText: String = "",         // Button text
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(32.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(24.dp))

            if (actionText.isNotEmpty()) {
                FilledTonalButton(
                    onClick = onActionClick,
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    actionIcon?.let { icon ->
                        Icon(
                            imageVector = icon,
                            contentDescription = actionText,
                            modifier = Modifier.size(ButtonDefaults.IconSize)
                        )
                        Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                    }
                    Text(actionText)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EmptyStateScreenWithCtaPreview() {
    EmptyStateScreenWithCta(
        icon = Icons.Default.VideoLibrary,
        title = "No matching videos",
        description = "Nothing",
        actionIcon = Icons.Default.AddHome,
        actionText = "Add Property",
        onActionClick = {}
    )
}