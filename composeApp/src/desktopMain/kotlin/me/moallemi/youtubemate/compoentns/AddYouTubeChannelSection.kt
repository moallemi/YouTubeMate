package me.moallemi.youtubemate.compoentns

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.moallemi.youtubemate.feature.addchannel.AddYouTubeChannel
import me.moallemi.youtubemate.model.Channel

@Composable
fun AddYouTubeChannelSection(
  channel: Channel?,
  error: String?,
  isLoading: Boolean,
  saveChannel: (String) -> Unit,
) {
  Box(
    modifier = Modifier
      .fillMaxSize()
      .wrapContentWidth(),
    contentAlignment = Alignment.Center,
  ) {
    AddYouTubeChannel(
      modifier = Modifier
        .width(400.dp),
      channel = channel,
      error = error,
      isLoading = isLoading,
      onSaveClick = saveChannel,
    )
  }
}
