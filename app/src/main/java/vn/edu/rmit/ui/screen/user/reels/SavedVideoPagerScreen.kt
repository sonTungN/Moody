package vn.edu.rmit.ui.screen.user.reels

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import vn.edu.rmit.ui.component.video.VideoDetailScreen

@Composable
fun SavedVideoPagerScreen(
    onHomeCtaClick: () -> Unit,
    onBookingClick: (id: String) -> Unit,
    onDetailClick: (id: String) -> Unit,
    viewModel: VideoPagerViewModel = hiltViewModel(),
) {
    val videos by viewModel.videos.collectAsState()
    val pagerState = rememberPagerState(pageCount = { videos.size })

    LaunchedEffect("saved_video") {
        viewModel.loadSavedVideo()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        VerticalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            val video = videos[page]
            VideoDetailScreen(
                video = video,
                onDetailClick = onDetailClick,
                onBookingClick = onBookingClick,
            )
        }
    }
}