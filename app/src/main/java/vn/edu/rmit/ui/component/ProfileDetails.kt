package vn.edu.rmit.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bloodtype
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import vn.edu.rmit.R

@Composable
fun ProfileDetails(
    name: String,
    role: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(name, style = MaterialTheme.typography.headlineMedium)
//            Text(email, style = MaterialTheme.typography.labelMedium)
            AssistChip(
                onClick = {},
                leadingIcon = {
                    Icon(Icons.Default.ManageAccounts,
                        stringResource(R.string.role),
                        modifier = Modifier.size(AssistChipDefaults.IconSize)
                    )
                },
                label = { Text(role) },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileDetailsPreview() {
    ProfileDetails(
        name = "John Doe",
//        email = "doe@gmail.com",
        role = "Donor"
    )
}