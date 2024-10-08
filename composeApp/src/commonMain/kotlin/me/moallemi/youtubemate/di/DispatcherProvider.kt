package me.moallemi.youtubemate.di

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface DispatcherProvider {
  fun main(): CoroutineDispatcher

  fun default(): CoroutineDispatcher

  fun io(): CoroutineDispatcher

  fun unconfined(): CoroutineDispatcher
}

internal class DefaultDispatcherProvider : DispatcherProvider {
  override fun main(): CoroutineDispatcher =
    Dispatchers.Main

  override fun default(): CoroutineDispatcher =
    Dispatchers.Default

  override fun io(): CoroutineDispatcher =
    Dispatchers.IO

  override fun unconfined(): CoroutineDispatcher =
    Dispatchers.Unconfined
}
