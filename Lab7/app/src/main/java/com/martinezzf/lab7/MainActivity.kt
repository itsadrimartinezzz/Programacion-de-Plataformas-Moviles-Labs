package com.martinezzf.lab7

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.graphics.Color

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PinkTheme {
                NotiScreen(generateFakeNotifications())
            }
        }
    }
}

@Composable
fun PinkTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        darkColorScheme(
            primary = Color(0xFFFF80AB),
            secondary = Color(0xFFF06292),
            tertiary = Color(0xFFEC407A)
        )
    } else {
        lightColorScheme(
            primary = Color(0xFFE91E63),       
            secondary = Color(0xFFF48FB1),
            tertiary = Color(0xFFF06292)
        )
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography(),
        content = content
    )
}
