package com.example.recorladora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.recorladora.repository.AppContainer
import com.example.recorladora.ui.theme.RecorladoraApplicationTheme
import com.example.recorladora.viewmodel.navigation.AppNavGraph

class MainActivity : ComponentActivity() {
    private val container = AppContainer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RecorladoraApplicationTheme { AppNavGraph(container) }
        }
    }
}