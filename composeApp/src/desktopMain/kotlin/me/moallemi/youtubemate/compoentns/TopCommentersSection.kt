package me.moallemi.youtubemate.compoentns

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
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

@Composable
fun TopCommentersSection(
  topCommentAuthors: Map<CommentAuthor, List<Comment>>,
  modifier: Modifier = Modifier,
) {
  LazyColumn(
    modifier = modifier
      .fillMaxSize(),
    contentPadding = PaddingValues(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp),
  ) {
    topCommentAuthors.forEach { commenter ->
      item(
        key = commenter.key.name,
      ) {
        Row(
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
          OutlinedButton(onClick = { /* Open commenter"s profile */ }) {
            Text("View Profile")
          }
        }
      }
    }
  }
}
