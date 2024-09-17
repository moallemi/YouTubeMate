package me.moallemi.youtubemate.compoentns

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import me.moallemi.youtubemate.model.Comment
import me.moallemi.youtubemate.model.CommentAuthor

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TopCommentersSection(
  topCommentAuthors: Map<CommentAuthor, List<Comment>>,
  isLoading: Boolean,
  modifier: Modifier = Modifier,
) {
  AnimatedVisibility(
    visible = isLoading,
  ) {
    Box {
      CircularProgressIndicator(
        modifier = Modifier
          .fillMaxSize()
          .wrapContentSize(),
      )
    }
  }

  LazyColumn(
    modifier = modifier
      .fillMaxSize(),
    contentPadding = PaddingValues(16.dp),
  ) {
    topCommentAuthors.forEach { commenter ->
      item(
        key = commenter.key.name,
      ) {
        Row(
          modifier = Modifier
            .animateItemPlacement()
            .clip(CircleShape)
            .clickable(onClick = { /* TODO */ })
            .padding(horizontal = 8.dp, vertical = 8.dp),
          horizontalArrangement = Arrangement.spacedBy(8.dp),
          verticalAlignment = Alignment.CenterVertically,
        ) {
          AsyncImage(
            model = ImageRequest.Builder(LocalPlatformContext.current)
              .data(commenter.key.avatarUrl)
              .placeholderMemoryCacheKey(commenter.key.name)
              .build(),
            contentDescription = null,
            modifier = Modifier
              .size(50.dp)
              .clip(CircleShape),
          )

          Column {
            Text(text = commenter.key.name)
            Text(
              text = " Comments: ${commenter.value.size}",
              style = MaterialTheme.typography.bodyMedium,
              color = MaterialTheme.colorScheme.secondary,
            )
          }
          Spacer(modifier = Modifier.weight(1f))
        }
      }
    }
  }
}
