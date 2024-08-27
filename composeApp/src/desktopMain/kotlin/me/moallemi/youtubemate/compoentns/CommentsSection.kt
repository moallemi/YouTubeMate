package me.moallemi.youtubemate.compoentns

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CommentsSection(
  items: List<Comment>,
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

  if (isLoading) {
    return
  }

  LazyColumn(
    modifier = modifier
      .fillMaxSize(),
    contentPadding = PaddingValues(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp),
  ) {
    items(
      items = items,
      key = { it.id },
    ) { comment ->
      Row(
        modifier = Modifier
          .animateItemPlacement(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
      ) {
        AsyncImage(
          model = ImageRequest.Builder(LocalPlatformContext.current)
            .data(comment.author.avatarUrl)
            .placeholderMemoryCacheKey(comment.author.name)
            .build(),
          contentDescription = null,
          modifier = Modifier
            .size(50.dp)
            .clip(CircleShape),
        )

        Column(
          verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
          ) {
            Text(
              text = comment.author.name,
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
              text = comment.date.substring(0, 10),
              style = MaterialTheme.typography.bodyMedium,
              color = MaterialTheme.colorScheme.secondary,
            )
          }
          Text(
            text = comment.text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
          )
        }
        Spacer(modifier = Modifier.weight(1f))
      }
    }
  }
}
