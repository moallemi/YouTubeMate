package me.moallemi.youtubemate

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import me.moallemi.youtubemate.data.Result
import me.moallemi.youtubemate.data.Result.Failure
import me.moallemi.youtubemate.di.DependencyContainer
import me.moallemi.youtubemate.di.DependencyProvider
import me.moallemi.youtubemate.feature.addchannel.AddYouTubeChannel
import me.moallemi.youtubemate.feature.addyoutubecred.AddYouTubeCredential
import me.moallemi.youtubemate.model.Channel
import me.moallemi.youtubemate.model.YouTubeCredential
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
  MaterialTheme {
    val dependencyContainer = remember { DependencyContainer(DependencyProvider()) }

    val youtubeApiKey by dependencyContainer.dataRepository.observeYouTubeCredential().collectAsState(null)
    val channel by dependencyContainer.dataRepository.observeChannel().collectAsState(null)

    if (youtubeApiKey == null) {
      YouTubeApiKey(
        youtubeApiKey = youtubeApiKey,
        dependencyContainer = dependencyContainer,
      )
    }
    if (youtubeApiKey != null && channel == null) {
      AddYouTubeChannel(
        channel = channel,
        dependencyContainer = dependencyContainer,
      )
    }
  }
}

@Composable
private fun AddYouTubeChannel(
  channel: Channel?,
  dependencyContainer: DependencyContainer,
) {
  val scope = rememberCoroutineScope()
  var isLoading by remember { mutableStateOf(false) }
  var error by remember { mutableStateOf<String?>(null) }
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
      onSaveClick = { channelId ->
        scope.launch {
          isLoading = true
          val result = dependencyContainer.dataRepository.channel(channelId)
          if (result is Failure) {
            error = "Invalid channel id"
          }
          isLoading = false
        }
      },
    )
  }
}

@Composable
private fun YouTubeApiKey(
  youtubeApiKey: YouTubeCredential?,
  dependencyContainer: DependencyContainer,
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
      onSaveClick = { apiKey ->
        dependencyContainer.dataRepository.storeYouTubeCredential(
          YouTubeCredential(apiKey = apiKey),
        )
      },
    )
  }
}
