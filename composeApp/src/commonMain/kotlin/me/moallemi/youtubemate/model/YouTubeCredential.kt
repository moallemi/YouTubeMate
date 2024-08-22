package me.moallemi.youtubemate.model

import kotlinx.serialization.Serializable

@Serializable
data class YouTubeCredential(
  val apiKey: String,
)
