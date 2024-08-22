package me.moallemi.youtubemate.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

class LocalStoreImpl(
  private val dataStore: DataStore<Preferences>,
) : LocalStore
