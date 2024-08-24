package me.moallemi.youtubemate.data

import kotlinx.coroutines.flow.Flow
import me.moallemi.youtubemate.model.Channel
import me.moallemi.youtubemate.model.Video
import me.moallemi.youtubemate.model.YouTubeCredential

interface LocalStore {
  suspend fun storeYouTubeCredential(credential: YouTubeCredential)

  fun observeYouTubeCredential(): Flow<YouTubeCredential?>

  suspend fun storeChannel(channel: Channel)

  fun observeChannel(): Flow<Channel?>

  suspend fun storeVideos(videos: List<Video>)

  fun observeVideos(): Flow<List<Video>>
}
