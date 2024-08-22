package me.moallemi.youtubemate.data

import kotlinx.coroutines.flow.Flow
import me.moallemi.youtubemate.model.YouTubeCredential

interface LocalStore {
  suspend fun storeYouTubeCredential(credential: YouTubeCredential)

  fun observeYouTubeCredential(): Flow<YouTubeCredential?>
}
