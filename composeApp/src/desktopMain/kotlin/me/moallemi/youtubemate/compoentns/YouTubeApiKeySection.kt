package me.moallemi.youtubemate.compoentns

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.moallemi.youtubemate.feature.addyoutubecred.AddYouTubeCredential
import me.moallemi.youtubemate.model.YouTubeCredential

@Composable
fun YouTubeApiKeySection(
  youtubeApiKey: YouTubeCredential?,
  onSave: (String) -> Unit,
) {
  Box(
    modifier = Modifier
      .fillMaxSize()
      .wrapContentWidth(),
    contentAlignment = Alignment.Center,
  ) {
    AddYouTubeCredential(
      modifier = Modifier
        .width(400.dp),
      youTubeCredential = youtubeApiKey,
      onSaveClick = onSave,
    )
  }
}
