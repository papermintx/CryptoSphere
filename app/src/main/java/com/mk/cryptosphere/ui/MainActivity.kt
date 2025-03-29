package com.mk.cryptosphere.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.mk.cryptosphere.ui.navigation.Navigation
import com.mk.cryptosphere.ui.theme.CryptoSphereTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CryptoSphereTheme {
                Navigation()
            }
        }
    }
}
