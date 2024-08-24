package me.moallemi.youtubemate.feature.addchannel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.moallemi.youtubemate.model.Channel

@Composable
fun AddYouTubeChannel(
  channel: Channel?,
  error: String?,
  isLoading: Boolean,
  modifier: Modifier = Modifier,
  onSaveClick: (String) -> Unit,
) {
  ElevatedCard {
    Column(
      modifier = modifier
        .padding(24.dp),
      verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
      var apiKey by remember(channel) { mutableStateOf(channel?.id.orEmpty()) }
      var isError by remember(error) { mutableStateOf(error != null) }

      TextField(
        modifier = Modifier
          .fillMaxWidth(),
        value = apiKey,
        onValueChange = {
          apiKey = it
          isError = false
        },
        label = { Text(text = "YouTube Channel ID") },
        singleLine = true,
        supportingText = {
          if (isError) {
            Text(
              text = error ?: "Channel ID cannot be empty",
            )
          }
        },
        isError = isError,
      )

      Button(
        modifier = Modifier
          .fillMaxWidth(),
        onClick = {
          if (isLoading) {
            return@Button
          }

          if (apiKey.trim().isEmpty()) {
            isError = true
          } else {
            isError = false
            onSaveClick(apiKey.trim())
          }
        },
      ) {
        if (isLoading) {
          CircularProgressIndicator(
            modifier = Modifier
              .fillMaxWidth()
              .wrapContentSize()
              .size(24.dp),
            color = MaterialTheme.colorScheme.onPrimary,
            strokeWidth = 3.dp,
          )
        } else {
          Text(text = "Save")
        }
      }
    }
  }
}
