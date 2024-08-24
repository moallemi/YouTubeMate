package me.moallemi.youtubemate.data

import com.google.api.services.youtube.YouTube
import kotlinx.coroutines.suspendCancellableCoroutine
import me.moallemi.youtubemate.model.Channel
import me.moallemi.youtubemate.model.Stats
import me.moallemi.youtubemate.model.YouTubeCredential
import kotlin.coroutines.resume

class YouTubeRemoteSourceImpl(
  private val youTube: YouTube,
) : YouTubeRemoteSource {
  override suspend fun channel(
    channelId: String,
    youTubeCredential: YouTubeCredential,
  ): Result<Channel, GeneralError> =
    suspendCancellableCoroutine { continuation ->
      try {
        val channel = youTube.channels()
          .list(listOf("snippet", "statistics"))
          .apply {
            this.id = listOf(channelId)
            key = youTubeCredential.apiKey
          }
          .execute()?.items?.get(0)!!.let { youtubeChannel ->
          Channel(
            id = youtubeChannel.id,
            title = youtubeChannel.snippet.title,
            handle = youtubeChannel.snippet.customUrl,
            thumbnail = youtubeChannel.snippet.thumbnails.medium.url,
            stats = Stats(
              videoCount = youtubeChannel.statistics.videoCount,
              subscriberCount = youtubeChannel.statistics.subscriberCount,
            ),
          )
        }
        continuation.resume(Result.Success(channel))
      } catch (e: Exception) {
        continuation.resume(Result.Failure(GeneralError.ApiError(e.message, -1)))
      }
    }
}
