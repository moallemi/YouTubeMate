package me.moallemi.youtubemate.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import me.moallemi.youtubemate.model.Channel
import me.moallemi.youtubemate.model.YouTubeCredential

class DataRepositoryImpl(
  private val youTubeRemoteSource: YouTubeRemoteSource,
  private val localStore: LocalStore,
  private val appScope: CoroutineScope,
) : DataRepository {
  override suspend fun channel(): Result<Channel, GeneralError> = youTubeRemoteSource.channel("")

  override fun observeYouTubeCredential(): Flow<YouTubeCredential?> = localStore.observeYouTubeCredential()

  override fun storeYouTubeCredential(credential: YouTubeCredential) {
    appScope.launch {
      localStore.storeYouTubeCredential(credential)
    }
  }
}
