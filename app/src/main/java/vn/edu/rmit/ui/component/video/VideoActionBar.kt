package vn.edu.rmit.ui.component.video

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Mood
import androidx.compose.material.icons.filled.Filter
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import vn.edu.rmit.R

@Composable
fun VideoActionBar(
    likes: Int,
    isLiked: Boolean,
    isSaved: Boolean,
    filterCount: Number,
    onFilterClick: () -> Unit,
    onLikeClick: () -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.wrapContentWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            IconButton(
                onClick = { onFilterClick() },
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Mood,
                    contentDescription = "filter",
                    modifier = Modifier.size(32.dp),
                    tint = Color(0xFFFFFFFF)
                )
            }
            Text(
                text = filterCount.toString(),
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            IconToggleButton(
                checked = isLiked,
                onCheckedChange = { onLikeClick() },
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = if (isLiked) Icons.Filled.Favorite
                    else Icons.Default.FavoriteBorder,
                    contentDescription = "favorite",
                    tint = Color(0xffE91E63),
                    modifier = Modifier.size(32.dp)
                )
            }
            Text(
                text = likes.toString(),
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            IconToggleButton(
                checked = isSaved,
                onCheckedChange = { onSaveClick() },
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = if (isSaved) Icons.Filled.Bookmark
                    else Icons.Default.BookmarkBorder,
                    contentDescription = "save",
                    tint = Color(0xfffbc826),
                    modifier = Modifier.size(32.dp)
                )
            }
            Text(
                text = if (isSaved) stringResource(R.string.saved) else stringResource(R.string.save),
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
            )
        }
    }
}

@Preview(showBackground = false)
@Composable
fun VideoActionBarPreview() {
    VideoActionBar(
        likes = 100,
        isLiked = false,
        isSaved = false,
        filterCount = 5,
        onLikeClick = {},
        onFilterClick = {},
        onSaveClick = {}
    )
}

@Preview(showBackground = false)
@Composable
fun VideoActionBarToggledPreview() {
    VideoActionBar(
        likes = 100,
        isLiked = true,
        isSaved = true,
        filterCount = 10,
        onLikeClick = {},
        onFilterClick = {},
        onSaveClick = {}
    )
}