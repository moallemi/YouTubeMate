package me.moallemi.youtubemate

class Greeting {
  private val platform = getPlatform()

  fun greet(): String {
    return "Hello, ${platform.name}!"
  }
}
