package me.moallemi.youtubemate.data

import me.moallemi.youtubemate.model.Channel
import me.moallemi.youtubemate.model.Comment
import me.moallemi.youtubemate.model.Video
import me.moallemi.youtubemate.model.YouTubeCredential

interface YouTubeRemoteSource {
  suspend fun channel(
    channelId: String,
    youTubeCredential: YouTubeCredential,
  ): Result<Channel, GeneralError>

  suspend fun allVideos(
    channelId: String,
    youTubeCredential: YouTubeCredential,
  ): Result<List<Video>, GeneralError>

  suspend fun allComments(
    videoIds: List<String>,
    youTubeCredential: YouTubeCredential,
  ): Result<List<Comment>, GeneralError>
}
