package me.moallemi.youtubemate.data

import kotlinx.coroutines.flow.Flow
import me.moallemi.youtubemate.model.Channel
import me.moallemi.youtubemate.model.YouTubeCredential

interface DataRepository {
  suspend fun channel(): Result<Channel, GeneralError>

  fun observeYouTubeCredential(): Flow<YouTubeCredential?>

  fun storeYouTubeCredential(credential: YouTubeCredential)
}
