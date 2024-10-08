package me.moallemi.youtubemate.model

import kotlinx.serialization.Serializable

@Serializable
data class Comment(
  val id: String,
  val text: String,
  val date: String,
  val videoId: String,
  val author: CommentAuthor,
)

@Serializable
data class CommentAuthor(
  val name: String,
  val avatarUrl: String,
  val profileLink: String,
)
