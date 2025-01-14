package vn.edu.rmit.ui.component.select

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import vn.edu.rmit.R
import vn.edu.rmit.data.model.type.Role

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoleSelect(
    roleValue: Role?,
    onRoleChange: (Role) -> Unit,
    roles: List<Role>,
    modifier: Modifier = Modifier
) {
    var isExpanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = {
            isExpanded = !isExpanded
        },
        modifier = modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = roleValue?.name ?: "",
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
            },
            label = { Text(stringResource(R.string.role)) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = {
                isExpanded = !isExpanded
            },
        ) {
            for (role in roles) {
                DropdownMenuItem(
                    text = { Text(role.name) },
                    onClick = {
                        onRoleChange(role)
                        isExpanded = !isExpanded
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RoleSelectPreview() {
    RoleSelect(
        roleValue = Role("1", "Admin"),
        onRoleChange = {},
        roles = listOf(Role("1", "Admin"), Role("2", "User"))
    )
}