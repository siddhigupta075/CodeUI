package com.example.codeuitrial2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.codeuitrial2.ui.navigation.MainApp
import com.example.codeuitrial2.ui.theme.CodeUiTrial2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CodeUiTrial2Theme {
                MainApp()
            }
        }
    }
}