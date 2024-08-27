import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
  alias(libs.plugins.kotlinMultiplatform)
  alias(libs.plugins.jetbrainsCompose)
  alias(libs.plugins.compose.compiler)
  alias(libs.plugins.kotlin.serialization)
}

kotlin {
  jvm("desktop")

  sourceSets {
    val desktopMain by getting

    commonMain.dependencies {
      implementation(compose.runtime)
      implementation(compose.foundation)
      implementation(compose.material3)
      implementation(compose.materialIconsExtended)
      implementation(compose.ui)
      implementation(compose.components.resources)
      implementation(compose.components.uiToolingPreview)
      implementation(libs.androidx.lifecycle.viewmodel)
      implementation(libs.androidx.lifecycle.runtime.compose)

      implementation(libs.androidx.datastore.preferences.core)
      implementation(libs.kotlinx.serialization.json)

      implementation(libs.coil)
      implementation(libs.coil.compose)
      implementation(libs.coil.network.okhttp)

      implementation(libs.ktor.client.core)
      implementation(libs.ktor.client.logging)
      implementation(libs.ktor.client.content.negotation)
      implementation(libs.ktor.client.kotlinx.serialization)

      implementation(libs.google.api.client)
      implementation(libs.google.api.services.youtube)
    }
    desktopMain.dependencies {
      implementation(compose.desktop.currentOs)
      implementation(libs.kotlinx.coroutines.swing)

      implementation(libs.ktor.client.okhttp)
    }
  }
}

compose.desktop {
  application {
    mainClass = "me.moallemi.youtubemate.MainKt"

    nativeDistributions {
      targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
      packageName = "me.moallemi.youtubemate"
      packageVersion = "1.0.0"
    }
  }
}
