package vn.edu.rmit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import vn.edu.rmit.ui.theme.MoodScapeTheme

@AndroidEntryPoint
class MoodScapeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoodScapeTheme {
                MoodScapeApplication()
            }
        }
    }
}