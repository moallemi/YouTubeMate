package me.moallemi.youtubemate.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import me.moallemi.youtubemate.model.YouTubeCredential

class LocalStoreImpl(
  private val dataStore: DataStore<Preferences>,
) : LocalStore {
  private val youtubeApiKeyPref = stringPreferencesKey("youtube_api_key")

  override suspend fun storeYouTubeCredential(credential: YouTubeCredential) {
    dataStore.edit { preferences ->
      preferences[youtubeApiKeyPref] = Json.encodeToString(credential)
    }
  }

  override fun observeYouTubeCredential(): Flow<YouTubeCredential?> =
    dataStore.data
      .map { preferences ->
        val jsonString = preferences[youtubeApiKeyPref] ?: return@map null
        Json.decodeFromString(jsonString)
      }
}
