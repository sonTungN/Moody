package vn.edu.rmit.ui.component.form

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import vn.edu.rmit.R
import vn.edu.rmit.data.model.Role
import vn.edu.rmit.ui.component.field.EmailField
import vn.edu.rmit.ui.component.field.PasswordField
import vn.edu.rmit.ui.component.select.RoleSelect

@Composable
fun RegisterForm(
    roles: List<Role>,
    onRegisterClick: (name: String, role: Role, email: String, password: String) -> Unit,
    onLoginClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var name by remember { mutableStateOf("") }
    var role by remember { mutableStateOf<Role?>(null) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(
            6.dp,
            Alignment.CenterVertically
        )
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            placeholder = { Text(stringResource(R.string.name)) },
            modifier = Modifier.fillMaxWidth().padding(bottom = 9.dp)
        )

        RoleSelect(
            roles = roles,
            roleValue = role,
            onRoleChange = { role = it },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )

        EmailField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier.fillMaxWidth()
        )

        PasswordField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.End)
        ) {
            TextButton(content = {
                Text(stringResource(R.string.login))
            }, onClick = { onLoginClick() })

            Button(
                content = {
                    Text(stringResource(R.string.register))
                },
                enabled = name.isNotBlank() && role != null &&
                        email.isNotBlank() && password.isNotBlank(),
                onClick = {
                    if (role !== null) onRegisterClick(
                        name,
                        role!!,
                        email,
                        password
                    )
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterFormPreview() {
    RegisterForm(
        roles = listOf(Role("Traveler"), Role("Owner")),
        onRegisterClick = { name, role, email, password ->  },
        onLoginClick = {},
    )
}