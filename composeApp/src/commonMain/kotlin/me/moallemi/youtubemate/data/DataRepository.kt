package me.moallemi.youtubemate.data

import kotlinx.coroutines.flow.Flow
import me.moallemi.youtubemate.model.Channel
import me.moallemi.youtubemate.model.Video
import me.moallemi.youtubemate.model.YouTubeCredential

interface DataRepository {
  suspend fun channel(channelId: String): Result<Channel, GeneralError>

  fun observeChannel(): Flow<Channel?>

  fun observeYouTubeCredential(): Flow<YouTubeCredential?>

  fun storeYouTubeCredential(credential: YouTubeCredential)

  fun observeVideos(): Flow<List<Video>>

  suspend fun allVideos(channelId: String): Result<List<Video>, GeneralError>
}
