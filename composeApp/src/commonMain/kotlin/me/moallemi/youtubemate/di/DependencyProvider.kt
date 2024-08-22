package me.moallemi.youtubemate.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.youtube.YouTube
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import okio.Path.Companion.toPath

class DependencyProvider {
  fun providesDataStore(): DataStore<Preferences> =
    PreferenceDataStoreFactory.createWithPath(
      corruptionHandler = null,
      migrations = emptyList(),
      produceFile = { DATASTORE_FILE_NAME.toPath() },
    )

  fun providesYouTube(): YouTube =
    YouTube.Builder(
      GoogleNetHttpTransport.newTrustedTransport(),
      GsonFactory(),
    ) { }.setApplicationName(APPLICATION_NAME)
      .build()

  fun providesAppScope(): CoroutineScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

  companion object {
    private const val DATASTORE_FILE_NAME = "youtubemate.preferences_pb"
    private const val APPLICATION_NAME = "YoutubeMate"
  }
}
