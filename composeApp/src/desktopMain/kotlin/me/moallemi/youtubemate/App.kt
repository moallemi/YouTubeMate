package me.moallemi.youtubemate

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.moallemi.youtubemate.di.DependencyContainer
import me.moallemi.youtubemate.di.DependencyProvider
import me.moallemi.youtubemate.feature.addyoutubecred.AddYouTubeCredential
import me.moallemi.youtubemate.model.YouTubeCredential
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
  MaterialTheme {
    val dependencyContainer = remember { DependencyContainer(DependencyProvider()) }

    val youtubeApiKey by dependencyContainer.dataRepository.observeYouTubeCredential().collectAsState(null)

    if (youtubeApiKey == null) {
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
          onSaveClick = { apiKey ->
            dependencyContainer.dataRepository.storeYouTubeCredential(
              YouTubeCredential(apiKey = apiKey),
            )
          },
        )
      }
    }
  }
}
