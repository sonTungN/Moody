package vn.edu.rmit.ui.component.video

import android.util.Log
import androidx.annotation.OptIn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.media3.common.Player
import dagger.hilt.android.UnstableApi
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import vn.edu.rmit.data.model.Video
import vn.edu.rmit.ui.component.button.HomeSmallCta
import vn.edu.rmit.ui.component.video_player.VideoPlayer

@Composable
fun VideoDetailScreen(
    video: Video,
    videoViewModel: VideoDetailViewModel
) {
    val uiState by videoViewModel.uiState.collectAsState()

    if (uiState.playerState == VideoPlayerState.Default) {
        Log.d("VideoURL", video.url)
        videoViewModel.handleAction(VideoDetailAction.LoadData(video.url))
    }

    VideoDetailScreenHandler(
        video = video,
        uiState = uiState.playerState,
        player = videoViewModel.videoPlayer,
        handleAction = { action -> videoViewModel.handleAction(action = action) }
    )
}

@Composable
fun VideoDetailScreenHandler(
    video: Video,
    uiState: VideoPlayerState,
    player: Player,
    handleAction: (VideoDetailAction) -> Unit
) {
    DisposableEffect(Unit) {
        onDispose {
            player.pause()
            player.clearMediaItems()
        }
    }

    when (uiState) {
        is VideoPlayerState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Video Loading...")
            }
        }
        is VideoPlayerState.Success -> {
            VideoDetail(
                video = video,
                player = player,
                handleAction = handleAction
            )
        }
        else -> {}
    }
}

@OptIn(UnstableApi::class)
@Composable
fun VideoDetail(
    video: Video,
    player: Player,
    handleAction: (VideoDetailAction) -> Unit,
    videoViewModel: VideoDetailViewModel = hiltViewModel()
) {
    val uiState by videoViewModel.uiState.collectAsState()

    LaunchedEffect(video.id) {
        videoViewModel.observeVideoDetailActionStatus(video.id)
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                onClick = { handleAction(VideoDetailAction.ToggleVideo) }
        )
    ) {
        val (videoPlayer, sideBar, videoInfo, videoActionBtn, homeCta) = createRefs()

        VideoPlayer(
            player = player,
            modifier = Modifier.constrainAs(videoPlayer) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.matchParent
                height = Dimension.matchParent
            }.zIndex(0f)
        )

        VideoActionBar(
            likes = uiState.reactCount,
            isLiked = uiState.reactStatus,
            isSaved = uiState.saveStatus,
            comments = 0,
            onLikeClick = { videoViewModel.toggleReaction(videoId = video.id) },
            onCommentClick = { },
            onSaveClick = { videoViewModel.toggleSave(videoId = video.id) },
            modifier = Modifier.constrainAs(sideBar) {
                end.linkTo(parent.end, margin = 8.dp)
                bottom.linkTo(videoActionBtn.top, margin = 8.dp)
            }.zIndex(1f)
        )

        VideoInfo(
            author = "Son Tung",
            videoTitle = video.title,
            description = video.desc,
            moodTags = video.moodTags.map { it },
            modifier = Modifier.constrainAs(videoInfo) {
                start.linkTo(parent.start, margin = 16.dp)
                bottom.linkTo(videoActionBtn.top, margin = 16.dp)
                end.linkTo(sideBar.start, margin = 8.dp)
                width = Dimension.fillToConstraints
            }.zIndex(1f)
        )

        VideoActionButtons(
            onBookClick = {},
            onViewDetailClick = {},
            modifier = Modifier.constrainAs(videoActionBtn) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom, margin = 16.dp)
            }.zIndex(1f)
        )
    }

}
