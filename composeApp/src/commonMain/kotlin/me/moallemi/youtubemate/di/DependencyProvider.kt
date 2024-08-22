package me.moallemi.youtubemate.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath

class DependencyProvider {
  fun providesDataStore(): DataStore<Preferences> =
    PreferenceDataStoreFactory.createWithPath(
      corruptionHandler = null,
      migrations = emptyList(),
      produceFile = { DATASTORE_FILE_NAME.toPath() },
    )

  companion object {
    private const val DATASTORE_FILE_NAME = "youtubemate.preferences_pb"
  }
}
