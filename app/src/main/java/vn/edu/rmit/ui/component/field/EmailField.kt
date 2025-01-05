package vn.edu.rmit.ui.component.field

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
    val emailPattern = remember { Regex("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$") }
    val isError = value.isNotEmpty() && !value.matches(emailPattern)

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
        isError = isError,
        supportingText = {
            if (isError) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.invalid_email),
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        trailingIcon = {
            if (isError)
                Icon(
                    Icons.Filled.Error,
                    "error",
                    tint = MaterialTheme.colorScheme.error
                )
        }
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