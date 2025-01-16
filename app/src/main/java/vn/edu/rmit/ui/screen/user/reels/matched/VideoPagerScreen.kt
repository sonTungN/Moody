package vn.edu.rmit.ui.screen.user.reels.matched

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import vn.edu.rmit.data.model.type.Mood
import vn.edu.rmit.ui.component.mood.MoodDialog
import vn.edu.rmit.ui.component.video.VideoDetailScreen

@Composable
fun VideoPagerScreen(
    onBookingClick: (id: String) -> Unit,
    onDetailClick: (id: String) -> Unit,
    viewModel: VideoPagerViewModel = hiltViewModel(),
    initialSelectedMoods: List<String>
) {
    val uiState by viewModel.uiState.collectAsState()
    val pagerState = rememberPagerState(pageCount = { uiState.videos.size })

    var moodDialogOpen by remember { mutableStateOf(false) }
    var selectedMoods: List<Mood> by remember { mutableStateOf(emptyList()) }

    LaunchedEffect(initialSelectedMoods) {
        viewModel.loadVideosForMoodsString(initialSelectedMoods)
    }

    if (moodDialogOpen) {
        MoodDialog(
            selected = selectedMoods.toSet(),
            moods = uiState.moods,
            onDismiss = { moodDialogOpen = false },
            onConfirm = {
                selectedMoods = it.toList()
                moodDialogOpen = false
                viewModel.loadVideosForMoods(selectedMoods)
            }
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        VerticalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            val video = uiState.videos[page]
            VideoDetailScreen(
                video = video,
                filterCount = selectedMoods.size,
                onBookingClick = onBookingClick,
                onDetailClick = onDetailClick,
                onFilterClick = { moodDialogOpen = true },
            )
        }
    }
}