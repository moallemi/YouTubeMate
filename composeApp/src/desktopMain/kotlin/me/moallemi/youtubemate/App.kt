package me.moallemi.youtubemate

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import kotlinx.coroutines.launch
import me.moallemi.youtubemate.data.Result.Failure
import me.moallemi.youtubemate.di.DependencyContainer
import me.moallemi.youtubemate.di.DependencyProvider
import me.moallemi.youtubemate.feature.addchannel.AddYouTubeChannel
import me.moallemi.youtubemate.feature.addyoutubecred.AddYouTubeCredential
import me.moallemi.youtubemate.model.Channel
import me.moallemi.youtubemate.model.Comment
import me.moallemi.youtubemate.model.CommentAuthor
import me.moallemi.youtubemate.model.Video
import me.moallemi.youtubemate.model.YouTubeCredential
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
  MaterialTheme {
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
    if (youtubeApiKey != null && channel != null) {
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
      TopCommentersSection(
        topCommentAuthors = commentsByAuthor,
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

@OptIn(ExperimentalFoundationApi::class)
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
