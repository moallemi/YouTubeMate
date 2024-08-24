package me.moallemi.youtubemate.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import me.moallemi.youtubemate.model.Channel
import me.moallemi.youtubemate.model.Comment
import me.moallemi.youtubemate.model.Video
import me.moallemi.youtubemate.model.YouTubeCredential

class LocalStoreImpl(
  private val dataStore: DataStore<Preferences>,
  private val json: Json,
) : LocalStore {
  private val youtubeApiKeyPref = stringPreferencesKey("youtube_api_key")
  private val youtubeChannelPref = stringPreferencesKey("youtube_channel")
  private val youtubeVideosPref = stringPreferencesKey("youtube_videos")
  private val youtubeCommentsPref = stringPreferencesKey("youtube_comments")

  override suspend fun storeYouTubeCredential(credential: YouTubeCredential) {
    dataStore.edit { preferences ->
      preferences[youtubeApiKeyPref] = json.encodeToString(credential)
    }
  }

  override fun observeYouTubeCredential(): Flow<YouTubeCredential?> =
    dataStore.data
      .map { preferences ->
        val jsonString = preferences[youtubeApiKeyPref] ?: return@map null
        json.decodeFromString(jsonString)
      }

  override suspend fun storeChannel(channel: Channel) {
    dataStore.edit { preferences ->
      preferences[youtubeChannelPref] = json.encodeToString(channel)
    }
  }

  override fun observeChannel(): Flow<Channel?> =
    dataStore.data
      .map { preferences ->
        val jsonString = preferences[youtubeChannelPref] ?: return@map null
        json.decodeFromString(jsonString)
      }

  override suspend fun storeVideos(videos: List<Video>) {
    dataStore.edit { preferences ->
      preferences[youtubeVideosPref] = json.encodeToString(videos)
    }
  }

  override fun observeVideos(): Flow<List<Video>> =
    dataStore.data
      .map { preferences ->
        val jsonString = preferences[youtubeVideosPref] ?: "[]"
        json.decodeFromString(jsonString)
      }

  override suspend fun storeComments(comments: List<Comment>) {
    dataStore.edit { preferences ->
      preferences[youtubeCommentsPref] = json.encodeToString(comments)
    }
  }

  override fun observeComments(): Flow<List<Comment>> =
    dataStore.data
      .map { preferences ->
        val jsonString = preferences[youtubeCommentsPref] ?: "[]"
        json.decodeFromString(jsonString)
      }
}
