package me.moallemi.youtubemate.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.math.BigInteger

@Serializable
data class Channel(
  val id: String,
  val title: String,
  val handle: String,
  val stats: Stats,
  val thumbnail: String,
)

@Serializable
data class Stats(
  @Contextual val videoCount: BigInteger,
  @Contextual val subscriberCount: BigInteger,
)
