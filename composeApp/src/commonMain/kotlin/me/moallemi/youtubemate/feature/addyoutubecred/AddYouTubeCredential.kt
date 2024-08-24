package me.moallemi.youtubemate.feature.addyoutubecred

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import me.moallemi.youtubemate.model.YouTubeCredential

@Composable
fun AddYouTubeCredential(
  youTubeCredential: YouTubeCredential?,
  modifier: Modifier = Modifier,
  onSaveClick: (String) -> Unit,
) {
  ElevatedCard {
    Column(
      modifier = modifier
        .padding(24.dp),
      verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
      var apiKey by remember(youTubeCredential) { mutableStateOf(youTubeCredential?.apiKey.orEmpty()) }
      var passwordVisible by remember { mutableStateOf(false) }
      var isError by remember { mutableStateOf(false) }

      TextField(
        modifier = Modifier
          .fillMaxWidth(),
        value = apiKey,
        onValueChange = {
          apiKey = it
          isError = false
        },
        label = { Text(text = "YouTube Api Key") },
        singleLine = true,
        supportingText = {
          if (isError) {
            Text(
              text = "Password cannot be empty",
            )
          } else {
            Text(text = "You can get it from Google Cloud Console")
          }
        },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        isError = isError,
        trailingIcon = {
          val icon = if (passwordVisible) Icons.Rounded.Visibility else Icons.Rounded.VisibilityOff

          IconButton(onClick = { passwordVisible = !passwordVisible }) {
            Icon(
              imageVector = icon,
              contentDescription = if (passwordVisible) "Hide password" else "Show password",
            )
          }
        },
      )

      Button(
        modifier = Modifier
          .fillMaxWidth(),
        onClick = {
          if (apiKey.trim().isEmpty()) {
            isError = true
          } else {
            isError = false
            onSaveClick(apiKey.trim())
          }
        },
      ) {
        Text(text = "Save")
      }
    }
  }
}
