package com.example.recorladora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.recorladora.repository.AppContainer
import com.example.recorladora.ui.theme.RecorladoraApplicationTheme
import com.example.recorladora.presentation.navigation.AppNavGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RecorladoraApplicationTheme { AppNavGraph() }
        }
    }
}