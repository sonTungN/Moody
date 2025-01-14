package vn.edu.rmit.ui.component.field

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import vn.edu.rmit.R

@Composable
fun PasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    modifier: Modifier = Modifier
) {
    val isError = value.isNotEmpty() && value.length < 6
    var visible by rememberSaveable { mutableStateOf(false) }

    OutlinedTextField(
        singleLine = true,
        modifier = modifier,
        value = value,
        onValueChange = { onValueChange(it) },
        placeholder = { Text(stringResource(R.string.password)) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Lock,
                contentDescription = stringResource(R.string.password)
            )
        },
        visualTransformation = if (visible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = keyboardOptions.copy(
            keyboardType = KeyboardType.Password,
        ),
        isError = isError,
        trailingIcon = {
            if (!isError) {
                val image = if (visible)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                val description =
                    if (visible) stringResource(R.string.password_hide) else stringResource(R.string.password_show)

                IconButton(onClick = { visible = !visible }) {
                    Icon(imageVector = image, description)
                }

            } else {
                Icon(Icons.Filled.Error, "error",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        },
        supportingText = {
            if (isError) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "At least 6 characters required",
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
fun PasswordFieldPreview() {
    PasswordField(value = "somerandompassword", onValueChange = {})
}