package vn.edu.rmit.ui.component.video

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import vn.edu.rmit.R

@Composable
fun VideoActionBar(
    likes: Int,
    comments: Int,
    shares: Int,
    onLikeClick: () -> Unit,
    onCommentClick: () -> Unit,
    onSaveClick: () -> Unit,
    onShareClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.wrapContentWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        IconButton(
            modifier = Modifier.size(60.dp),
            onClick = onLikeClick
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    Icons.Filled.Favorite,
                    "favorite",
                    modifier = Modifier.size(35.dp),
                    tint = Color.White
                )
                Text(
                    text = likes.toString(),
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
                )
            }
        }

        IconButton(
            modifier = Modifier.size(60.dp),
            onClick = onCommentClick
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    Icons.Filled.ChatBubble,
                    "comments",
                    modifier = Modifier.size(35.dp),
                    tint = Color.White
                )
                Text(
                    text = comments.toString(),
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
                )
            }
        }

        IconButton(
            modifier = Modifier.size(60.dp),
            onClick = onSaveClick
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    Icons.Filled.Bookmark,
                    "save",
                    modifier = Modifier.size(35.dp),
                    tint = Color.White
                )
                Text(
                    text = stringResource(R.string.save),
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
                )
            }
        }

        IconButton(
            modifier = Modifier.size(60.dp),
            onClick = onShareClick
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    Icons.Filled.Share,
                    "share",
                    modifier = Modifier.size(35.dp),
                    tint = Color.White
                )
                Text(
                    text = stringResource(R.string.share),
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
                )
            }
        }
    }
}

@Preview (showBackground = false)
@Composable
fun VideoActionBarPreview() {
    VideoActionBar(
        likes = 100,
        comments = 50,
        shares = 20,
        onLikeClick = {},
        onCommentClick = { TODO() },
        onShareClick = { TODO() },
        onSaveClick = { TODO() }
    )
}