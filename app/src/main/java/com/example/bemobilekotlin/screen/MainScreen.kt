package com.example.bemobilekotlin.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.bemobilekotlin.componentes.OnBoard
import com.example.bemobilekotlin.ui.theme.BeMobileKotlinTheme

class MainScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BeMobileKotlinTheme {
                OnBoard()
            }
        }
    }
}