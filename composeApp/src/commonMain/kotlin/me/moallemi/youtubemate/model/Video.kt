package me.moallemi.youtubemate.model

import kotlinx.serialization.Serializable

@Serializable
data class Video(
  val id: String,
  val title: String,
  val thumbnail: String,
)
