package com.example.scientificcalculathor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.Modifier
import com.example.scientificcalculathor.ui.theme.ScientificCalculaTHORTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ScientificCalculaTHORTheme {
                Calculator(modifier = Modifier)
            }
        }
    }
}
