package com.malavet.exampleapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.malavet.exampleapplication.ui.profile.ProfilesApp
import com.malavet.exampleapplication.ui.theme.ExampleApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

/*
* Entry Point to Application, using Compose for Layout
* ExampleApplicationTheme is the default theme from a Android Studio template
* */

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExampleApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ProfilesApp()
                }
            }
        }
    }
}
