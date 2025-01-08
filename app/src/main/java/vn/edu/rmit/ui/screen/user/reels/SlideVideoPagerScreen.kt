package vn.edu.rmit.ui.screen.user.reels

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import kotlinx.coroutines.launch
import vn.edu.rmit.ui.component.tab_index.TabContentView
import vn.edu.rmit.ui.screen.user.reels.matched.VideoPagerScreen
import vn.edu.rmit.ui.screen.user.reels.saved.SavedVideoPagerScreen

@Composable
fun SlideVideoPagerScreen(
    onHomeCtaClick: () -> Unit,
    onBookingClick: (id: String) -> Unit,
    onDetailClick: (id: String) -> Unit,
    selectedMoods: List<String>
) {
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(
        pageCount = { 2 },
        initialPage = 1
    )
    val scrollToPager: (Boolean) -> Unit = { isMatchedVideos ->
        val pageIndex = if (isMatchedVideos) 1 else 0
        coroutineScope.launch {
            pagerState.scrollToPage(page = pageIndex)
        }
    }

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (tabContentView, pagerView) = createRefs()

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.constrainAs(pagerView) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            })
        { pageIndex ->
            when (pageIndex) {
                0 -> SavedVideoPagerScreen(onHomeCtaClick = onHomeCtaClick,
                    onBookingClick = onBookingClick,
                    onDetailClick = onDetailClick)
                1 -> VideoPagerScreen(
                    onHomeCtaClick = onHomeCtaClick,
                    onBookingClick = onBookingClick,
                    onDetailClick = onDetailClick,
                    selectedMoods = selectedMoods
                )
            }
        }

        TabContentView(
            onHomeCtaClick = onHomeCtaClick,
            isTabSelectedIndex = pagerState.currentPage,
            onSelectedTab = { isMatchedVideos -> scrollToPager(isMatchedVideos) },
            modifier = Modifier.constrainAs(tabContentView) {
                top.linkTo(parent.top, margin = 16.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            },
        )
    }
}