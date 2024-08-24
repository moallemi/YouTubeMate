package me.moallemi.youtubemate.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import me.moallemi.youtubemate.data.Result.Success
import me.moallemi.youtubemate.model.Channel
import me.moallemi.youtubemate.model.Video
import me.moallemi.youtubemate.model.YouTubeCredential

class DataRepositoryImpl(
  private val youTubeRemoteSource: YouTubeRemoteSource,
  private val localStore: LocalStore,
  private val appScope: CoroutineScope,
) : DataRepository {
  override suspend fun channel(channelId: String): Result<Channel, GeneralError> {
    val youTubeCredential = localStore.observeYouTubeCredential().first()
      ?: return Result.Failure(GeneralError.AppError("No YouTube Credential"))
    val result = youTubeRemoteSource.channel(channelId, youTubeCredential)
    if (result is Success) {
      localStore.storeChannel(result.data)
    }
    return result
  }

  override fun observeChannel(): Flow<Channel?> =
    localStore.observeChannel()

  override fun observeYouTubeCredential(): Flow<YouTubeCredential?> =
    localStore.observeYouTubeCredential()

  override fun storeYouTubeCredential(credential: YouTubeCredential) {
    appScope.launch {
      localStore.storeYouTubeCredential(credential)
    }
  }

  override fun observeVideos(): Flow<List<Video>> =
    localStore.observeVideos()

  override suspend fun allVideos(channelId: String): Result<List<Video>, GeneralError> {
    val youTubeCredential = localStore.observeYouTubeCredential().first()
      ?: return Result.Failure(GeneralError.AppError("No YouTube Credential"))

    val result = youTubeRemoteSource.allVideos(channelId, youTubeCredential)
    if (result is Success) {
      localStore.storeVideos(result.data)
    }
    return result
  }
}
