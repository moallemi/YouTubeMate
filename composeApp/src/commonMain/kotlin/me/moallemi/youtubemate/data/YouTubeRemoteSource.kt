package me.moallemi.youtubemate.data

import me.moallemi.youtubemate.model.Channel
import me.moallemi.youtubemate.model.YouTubeCredential

interface YouTubeRemoteSource {
  suspend fun channel(
    channelId: String,
    youTubeCredential: YouTubeCredential,
  ): Result<Channel, GeneralError>
}
