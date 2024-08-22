package me.moallemi.youtubemate.data

import me.moallemi.youtubemate.model.Channel

interface YouTubeRemoteSource {
  suspend fun channel(channelId: String): Result<Channel, GeneralError>
}
