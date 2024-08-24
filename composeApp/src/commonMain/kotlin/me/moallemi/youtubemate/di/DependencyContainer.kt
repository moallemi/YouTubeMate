package me.moallemi.youtubemate.di

import me.moallemi.youtubemate.data.DataRepositoryImpl
import me.moallemi.youtubemate.data.LocalStoreImpl
import me.moallemi.youtubemate.data.YouTubeRemoteSourceImpl

class DependencyContainer(
  private val provider: DependencyProvider,
) {
  val dataRepository by lazy {
    DataRepositoryImpl(
      youTubeRemoteSource = YouTubeRemoteSourceImpl(
        youTube = provider.providesYouTube(),
      ),
      localStore = LocalStoreImpl(
        dataStore = provider.providesDataStore(),
        json = provider.providesJson(),
      ),
      appScope = provider.providesAppScope(),
      dispatcher = provider.providesDispatcher(),
    )
  }
}
