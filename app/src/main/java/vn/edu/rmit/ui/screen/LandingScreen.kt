package vn.edu.rmit.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import vn.edu.rmit.R

@Composable
fun LandingScreen(
    onLogout: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LandingScreenViewModel = hiltViewModel()
) {
    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                painterResource(R.drawable.health_metrics),
                contentDescription = stringResource(R.string.app_name),
                modifier = Modifier
                    .size(128.dp)
                    .align(Alignment.CenterHorizontally),
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = MaterialTheme.colorScheme.primary
            )
        }

        FilledTonalButton(
            onClick = { viewModel.logout(onLogout) },
            modifier = modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            Text(stringResource(R.string.logout))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LandingScreenPreview() {
    LandingScreen(
        onLogout = {}
    )
}