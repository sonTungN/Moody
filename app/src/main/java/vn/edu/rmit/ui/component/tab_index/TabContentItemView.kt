package vn.edu.rmit.ui.component.tab_index

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import vn.edu.rmit.ui.component.button.HomeSmallCta
import vn.edu.rmit.ui.theme.MoodScapeTheme

@Composable
fun TabContentItemView(
    modifier: Modifier = Modifier,
    title: String,
    isSelected: Boolean,
    isMatchedVideos: Boolean,
    onSelectedTab: (isMatchedVideos: Boolean) -> Unit
) {
    val alpha = if (isSelected) 1f else 0.6f

    Column(
        modifier = modifier
            .wrapContentSize()
            .clickable { onSelectedTab(isMatchedVideos) },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(color = Color.White.copy(alpha = alpha))
        )
        Spacer(modifier = Modifier.height(4.dp))
        if (isSelected) {
            HorizontalDivider(
                modifier = Modifier.width(24.dp),
                thickness = 2.dp,
                color = Color.White
            )
        }
    }
}

@Composable
fun TabContentView(
    onHomeCtaClick: () -> Unit,
    modifier: Modifier = Modifier,
    isTabSelectedIndex: Int,
    onSelectedTab: (isMatchedVideos: Boolean) -> Unit,
) {

    ConstraintLayout(modifier = modifier.fillMaxWidth()) {
        val (tabContent, homeCta) = createRefs()
        Row(modifier = Modifier
            .wrapContentSize()
            .constrainAs(tabContent) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }) {
            TabContentItemView(
                title = "Saved",
                isSelected = isTabSelectedIndex == 0,
                isMatchedVideos = false,
                onSelectedTab = onSelectedTab
            )
            Spacer(modifier = Modifier.width(12.dp))
            TabContentItemView(
                title = "Recommended",
                isSelected = isTabSelectedIndex == 1,
                isMatchedVideos = true,
                onSelectedTab = onSelectedTab
            )
        }

        HomeSmallCta(
            onHomeCtaClick = onHomeCtaClick,
            modifier = Modifier
                .constrainAs(homeCta) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end, margin = 16.dp)
                }
                .zIndex(1f)
        )
    }

}

@Preview
@Composable
fun TabContentItemViewPreviewSelected() {
    MoodScapeTheme {
        TabContentItemView(
            title = "Saved Videos",
            isSelected = true,
            isMatchedVideos = true,
            onSelectedTab = {})
    }
}

@Preview
@Composable
fun TabContentItemViewPreviewUnSelected() {
    MoodScapeTheme {
        TabContentItemView(
            title = "Mood Matching",
            isSelected = false,
            isMatchedVideos = false,
            onSelectedTab = {})
    }
}

@Preview
@Composable
fun TabContentViewPreview() {
    MoodScapeTheme {
        TabContentView(
            onHomeCtaClick = {},
            isTabSelectedIndex = 1,
            onSelectedTab = { true }
        )
    }
}