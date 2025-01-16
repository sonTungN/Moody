package vn.edu.rmit.ui.component.select

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.TypeSpecimen
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import vn.edu.rmit.R
import vn.edu.rmit.data.model.type.PropertyType

@Composable
fun PropertyTypeSelect(
    propertyTypeValue: PropertyType?,
    onPropertyTypeChange: (PropertyType) -> Unit,
    propertyTypes: List<PropertyType>,
    modifier: Modifier = Modifier
) {
    var isExpanded by remember { mutableStateOf(false) }

    Box(modifier = modifier.wrapContentSize(Alignment.TopStart)) {
        FilterChip(
            selected = propertyTypeValue !== null,
            onClick = { isExpanded = !isExpanded },
            leadingIcon = {
                Icon(
                    Icons.Default.TypeSpecimen,
                    stringResource(R.string.property_type),
                    modifier = Modifier.size(AssistChipDefaults.IconSize)
                )
            },
            label = { Text(propertyTypeValue?.name ?: stringResource(R.string.property_type)) }
        )
        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = !isExpanded },
        ) {
            for (type in propertyTypes)
                DropdownMenuItem(
                    text = { Text(type.name) },
                    onClick = { onPropertyTypeChange(type) }
                )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PropertyTypeSelectPreview() {
    PropertyTypeSelect(
        propertyTypeValue = null,
        onPropertyTypeChange = {},
        propertyTypes = emptyList()
    )
}