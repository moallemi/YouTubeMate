package me.moallemi.youtubemate.data

import me.moallemi.youtubemate.model.Channel

interface DataRepository {
  suspend fun channel(): Result<Channel, GeneralError>
}
