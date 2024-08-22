package me.moallemi.youtubemate.model

import java.math.BigInteger

data class Channel(
  val id: String,
  val title: String,
  val handle: String,
  val stats: Stats,
  val thumbnail: String,
)

data class Stats(
  val videoCount: BigInteger,
  val subscriberCount: BigInteger,
)
