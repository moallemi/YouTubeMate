package me.moallemi.youtubemate

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import me.moallemi.youtubemate.compoentns.AddYouTubeChannelSection
import me.moallemi.youtubemate.compoentns.ChannelSection
import me.moallemi.youtubemate.compoentns.CommentsSection
import me.moallemi.youtubemate.compoentns.TopCommentersSection
import me.moallemi.youtubemate.compoentns.YouTubeApiKeySection
import me.moallemi.youtubemate.data.Result.Failure
import me.moallemi.youtubemate.di.DependencyContainer
import me.moallemi.youtubemate.di.DependencyProvider
import me.moallemi.youtubemate.model.Video
import me.moallemi.youtubemate.model.YouTubeCredential
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
  AppTheme {
    val dependencyContainer = remember { DependencyContainer(DependencyProvider()) }

    val youtubeApiKey by dependencyContainer.dataRepository.observeYouTubeCredential().collectAsState(null)
    val channel by dependencyContainer.dataRepository.observeChannel().collectAsState(null)
    val videos by dependencyContainer.dataRepository.observeVideos().collectAsState(initial = null)
    val comments by dependencyContainer.dataRepository.observeComments().collectAsState(initial = null)
    val commentsByAuthor by remember {
      derivedStateOf {
        comments?.groupBy {
          it.author
        }?.entries
          ?.sortedByDescending { it.value.size }
          ?.associate { it.key to it.value }
          ?: emptyMap()
      }
    }
    val topComments by remember {
      derivedStateOf {
        comments?.filter { comment ->
          comment.author.name != "@RezaDevs" && comment.text.length > 30
        }
      }
    }

    if (youtubeApiKey == null) {
      YouTubeApiKeySection(
        youtubeApiKey = youtubeApiKey,
        onSave = { apiKey ->
          dependencyContainer.dataRepository.storeYouTubeCredential(YouTubeCredential(apiKey))
        },
      )
    }
    if (youtubeApiKey != null && channel == null) {
      val scope = rememberCoroutineScope()
      var isLoading by remember { mutableStateOf(false) }
      var error by remember { mutableStateOf<String?>(null) }

      AddYouTubeChannelSection(
        channel = channel,
        isLoading = isLoading,
        error = error,
        saveChannel = { channelId ->
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
    if (youtubeApiKey != null && channel != null) {
      Column {
        ChannelSection(
          modifier = Modifier
            .padding(top = 16.dp)
            .padding(horizontal = 16.dp),
          channel = channel!!,
        )
        LaunchedEffect(Unit) {
          val cachedVideos: List<Video>
          if (videos?.isEmpty() == true) {
            val videoResult = dependencyContainer.dataRepository.allVideos(channel!!.id)
            if (videoResult is Failure) {
              // Handle error
            }
            cachedVideos = videoResult.successValue()!!
          } else {
            cachedVideos = videos ?: emptyList()
          }

          if (comments?.isEmpty() == true) {
            val commentsResult = dependencyContainer.dataRepository.allComments(cachedVideos.map { it.id })
            if (commentsResult is Failure) {
              // Handle error
            }
            dependencyContainer.dataRepository.allComments(cachedVideos.map { it.id })
          }
        }
        Row {
          ElevatedCard(
            modifier = Modifier
              .padding(vertical = 16.dp)
              .padding(start = 16.dp, end = 8.dp)
              .weight(1f),
          ) {
            Column {
              Text(
                text = "Top Commenters",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(16.dp),
              )
              TopCommentersSection(
                topCommentAuthors = commentsByAuthor,
              )
            }
          }

          ElevatedCard(
            modifier = Modifier
              .padding(vertical = 16.dp)
              .padding(start = 8.dp, end = 16.dp)
              .weight(1f),
          ) {
            Column {
              Text(
                text = "Top Comments",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(16.dp),
              )
              CommentsSection(
                items = topComments ?: emptyList(),
              )
            }
          }
        }
      }
    }
  }
}

@Composable
fun AppTheme(content: @Composable () -> Unit) {
  MaterialTheme(
    colorScheme = if (isSystemInDarkTheme()) darkColorScheme() else MaterialTheme.colorScheme,
  ) {
    Surface {
      content()
    }
  }
}
