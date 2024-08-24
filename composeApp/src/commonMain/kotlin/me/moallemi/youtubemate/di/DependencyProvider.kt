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
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import okio.Path.Companion.toPath
import java.math.BigInteger

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

  fun providesJson(): Json {
    val module =
      SerializersModule {
        contextual(BigIntegerSerializer)
      }
    return Json { serializersModule = module }
  }

  companion object {
    private const val DATASTORE_FILE_NAME = "youtubemate.preferences_pb"
    private const val APPLICATION_NAME = "YoutubeMate"
  }
}

object BigIntegerSerializer : KSerializer<BigInteger> {
  override val descriptor: SerialDescriptor =
    PrimitiveSerialDescriptor("BigInteger", PrimitiveKind.STRING)

  override fun serialize(
    encoder: Encoder,
    value: BigInteger,
  ) {
    encoder.encodeString(value.toString())
  }

  override fun deserialize(decoder: Decoder): BigInteger {
    return BigInteger(decoder.decodeString())
  }
}
