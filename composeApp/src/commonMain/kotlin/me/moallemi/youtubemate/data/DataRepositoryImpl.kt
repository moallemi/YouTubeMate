package me.moallemi.youtubemate.data

import me.moallemi.youtubemate.model.Channel

class DataRepositoryImpl(
  private val youTubeRemoteSource: YouTubeRemoteSource,
) : DataRepository {
  override suspend fun channel(): Result<Channel, GeneralError> = youTubeRemoteSource.channel("")
}
