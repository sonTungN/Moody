package vn.edu.rmit.ui.screen.user.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import vn.edu.rmit.R
import vn.edu.rmit.ui.component.card.StatisticCard
import vn.edu.rmit.ui.screen.LandingScreenViewModel
import vn.edu.rmit.ui.theme.AppTypography

@Composable
fun HomeScreen(
    onLogout: () -> Unit,
    onReservationClick: () -> Unit,
    onDonationClick: (id: String) -> Unit,
    viewModel: HomeScreenViewModel = hiltViewModel(),
    landingViewModel: LandingScreenViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

//    LaunchedEffect(selectedMoods) {
//        viewModel.updateSelectedMoods(selectedMoods)
//    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.padding(16.dp)
        ) {
            Text(stringResource(R.string.statistic), style = AppTypography.titleLarge)

            if (uiState.selectedMoods.isNotEmpty()) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Current Mood", style = AppTypography.titleMedium)

                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            contentPadding = PaddingValues(vertical = 8.dp)
                        ) {
                            items(uiState.selectedMoods) { mood ->
                                Card {
                                    Text(
                                        text = mood,
                                        modifier = Modifier.padding(
                                            horizontal = 12.dp, vertical = 6.dp
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                StatisticCard(
                    title = uiState.profile.fullName,
                    subtitle = "Full Name",
                    modifier = Modifier.weight(1f),
                )
            }
        }

        FilledTonalButton(
            onClick = { landingViewModel.logout(onLogout) }, modifier = modifier.padding(16.dp)
        ) {
            Text(stringResource(R.string.logout))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    HomeScreen(onReservationClick = {}, onDonationClick = {}, onLogout = {})
}