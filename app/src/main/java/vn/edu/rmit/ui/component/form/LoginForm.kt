package vn.edu.rmit.ui.component.form

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import vn.edu.rmit.R
import vn.edu.rmit.ui.component.field.EmailField
import vn.edu.rmit.ui.component.field.PasswordField

@Composable
fun LoginForm(
    onLoginClick: (email: String, password: String) -> Unit,
    onRegisterClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = modifier,

        verticalArrangement = Arrangement.spacedBy(
            6.dp,
            Alignment.CenterVertically
        )
    ) {
        EmailField(
            value = email,
            onValueChange = { email = it },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            modifier = Modifier.fillMaxWidth()
        )

        PasswordField(
            value = password,
            onValueChange = { password = it },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.End)
        ) {
            TextButton(content = {
                Text(stringResource(R.string.register))
            }, onClick = { onRegisterClick() })

            Button(
                content = {
                    Text(stringResource(R.string.login))
                },
                enabled = email.isNotBlank() && password.isNotBlank(),
                onClick = { onLoginClick(email, password) })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginForm(
        onLoginClick = { email, password -> },
        onRegisterClick = {}
    )
}