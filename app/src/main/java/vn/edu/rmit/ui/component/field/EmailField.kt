package vn.edu.rmit.ui.component.field

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import vn.edu.rmit.R

@Composable
fun EmailField(
    value: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        singleLine = true,
        modifier = modifier,
        value = value,
        onValueChange = { onValueChange(it) },
        placeholder = { Text(stringResource(R.string.email)) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Email,
                contentDescription = stringResource(R.string.email)
            )
        },
        keyboardOptions = keyboardOptions.copy(
            keyboardType = KeyboardType.Email,
        ),
        isError = !value.matches(Regex("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$"))
    )
}

@Preview(showBackground = true)
@Composable
fun EmailFieldPreview() {
    EmailField(
        value = "account@email.com",
        onValueChange = {}
    )
}