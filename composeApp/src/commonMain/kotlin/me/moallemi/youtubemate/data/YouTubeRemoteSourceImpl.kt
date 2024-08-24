package me.moallemi.youtubemate.data

import com.google.api.services.youtube.YouTube
import kotlinx.coroutines.suspendCancellableCoroutine
import me.moallemi.youtubemate.model.Channel
import me.moallemi.youtubemate.model.Stats
import me.moallemi.youtubemate.model.Video
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

  override suspend fun allVideos(
    channelId: String,
    youTubeCredential: YouTubeCredential,
  ): Result<List<Video>, GeneralError> =
    suspendCancellableCoroutine { continuation ->
      val request = youTube.channels().list(listOf("contentDetails"))
        .setKey(youTubeCredential.apiKey)
        .setId(listOf(channelId))
      val response = request.execute()
      val uploadPlaylistId = response.items[0].contentDetails.relatedPlaylists.uploads

      // List playlist items
      val playlistItemsRequest = youTube.playlistItems().list(listOf("snippet"))
        .setPlaylistId(uploadPlaylistId)
        .setMaxResults(50)
        .setKey(youTubeCredential.apiKey)

      val videos = mutableListOf<Video>()

      var nextPageToken: String? = null
      do {
        playlistItemsRequest.pageToken = nextPageToken
        val playlistItemsResponse = playlistItemsRequest.execute()
        val ids = playlistItemsResponse.items.map {
          Video(
            id = it.snippet.resourceId.videoId,
            title = it.snippet.title,
            thumbnail = it.snippet.thumbnails.default.url,
          )
        }
        videos.addAll(ids)

        nextPageToken = playlistItemsResponse.nextPageToken
      } while (nextPageToken != null)

      continuation.resume(Result.Success(videos))
    }
}
