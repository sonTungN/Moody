package vn.edu.rmit.ui.component.button

import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun FilterSmallCta(
    modifier: Modifier = Modifier,
    onFilterCtaClick: () -> Unit
) {
    Box(
        modifier = modifier
    ) {
        OutlinedButton(
            modifier = Modifier.size(40.dp),
            onClick = onFilterCtaClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(10.dp),
            contentPadding = PaddingValues(0.dp),
            border = ButtonDefaults.outlinedButtonBorder.copy(
                brush = SolidColor(Color.White)
            )
        ) {
            Icon(
                Icons.Filled.FilterAlt,
                contentDescription = "filter_cta",
                tint = Color.White,
            )
        }
    }
}

@Preview (showBackground = false)
@Composable
fun FilterSmallCtaPreview() {
    FilterSmallCta(
        onFilterCtaClick = {}
    )
}