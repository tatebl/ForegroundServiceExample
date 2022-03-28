package com.example.foregroundserviceexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.foregroundserviceexample.services.foregroundStartService
import com.example.foregroundserviceexample.ui.theme.ForegroundServiceExampleTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ForegroundServiceExampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Buttons()
                }
            }
        }
    }
}

@Preview
@Composable
fun Buttons() {
    val context = LocalContext.current
    Scaffold(topBar = {
        TopAppBar(backgroundColor = MaterialTheme.colors.primary,
            modifier =Modifier.fillMaxWidth(),
            title = {Text("Foreground Service")})
    }) {
        Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()) {

            Button(onClick = { context.foregroundStartService("Start") }) {
                Text(text = "Trigger")
            }

            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = { context.foregroundStartService("Stop") }) {
                Text(text = "Exit")
            }



        }
    }
}