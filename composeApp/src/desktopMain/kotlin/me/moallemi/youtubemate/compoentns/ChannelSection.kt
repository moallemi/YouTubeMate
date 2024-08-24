package me.moallemi.youtubemate.compoentns

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import me.moallemi.youtubemate.model.Channel

@Composable
fun ChannelSection(
  channel: Channel,
  modifier: Modifier = Modifier,
) {
  Row(
    modifier = modifier,
    horizontalArrangement = Arrangement.spacedBy(16.dp),
  ) {
    AsyncImage(
      model = ImageRequest.Builder(LocalPlatformContext.current)
        .data(channel.thumbnail)
        .placeholderMemoryCacheKey("channel_${channel.id}")
        .build(),
      contentDescription = null,
      modifier = Modifier
        .size(72.dp)
        .clip(CircleShape),
    )

    Column {
      Text(text = channel.title, style = MaterialTheme.typography.titleLarge)
      Text(
        text = "${channel.stats.subscriberCount} subscribers",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
      )
      Text(
        text = "${channel.stats.videoCount} videos",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
      )
    }
  }
}
