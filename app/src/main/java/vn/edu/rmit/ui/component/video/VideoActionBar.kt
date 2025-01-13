package vn.edu.rmit.ui.component.video

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import vn.edu.rmit.R

@Composable
fun VideoActionBar(
    likes: Int,
    isLiked: Boolean,
    isSaved: Boolean,
    comments: Int,
    onLikeClick: () -> Unit,
    onCommentClick: () -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.wrapContentWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        IconButton(
            modifier = Modifier.size(70.dp),
            onClick = onLikeClick
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconToggleButton(
                    modifier = Modifier.size(40.dp),
                    checked = isLiked,
                    onCheckedChange = { onLikeClick() }
                ) {
                    Icon(
                        modifier = Modifier.size(40.dp),
                        imageVector = if (isLiked) Icons.Filled.Favorite
                                        else Icons.Default.FavoriteBorder,
                        contentDescription = "favorite",
                        tint = Color(0xffE91E63),
                    )
                }
                Text(
                    text = likes.toString(),
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
                )
            }
        }

        /*
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
                    modifier = Modifier.padding(top = 4.dp),
                    text = comments.toString(),
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
                )
            }
        }
         */

        IconButton(
            modifier = Modifier.size(70.dp),
            onClick = onSaveClick
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconToggleButton(
                    modifier = Modifier.size(40.dp),
                    checked = isSaved,
                    onCheckedChange = { onSaveClick() }
                ) {
                    Icon(
                        modifier = Modifier.size(40.dp),
                        imageVector = if (isSaved) Icons.Filled.Bookmark
                            else Icons.Default.BookmarkBorder,
                        contentDescription = "save",
                        tint = Color(0xfffbc826),
                    )
                }
                Text(
                    text = if (isSaved) stringResource(R.string.saved) else stringResource(R.string.save),
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
        isLiked = false,
        isSaved = false,
        comments = 50,
        onLikeClick = {},
        onCommentClick = { TODO() },
        onSaveClick = { TODO() }
    )
}