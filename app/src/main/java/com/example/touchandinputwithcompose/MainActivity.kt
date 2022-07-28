package com.example.touchandinputwithcompose

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.touchandinputwithcompose.ui.theme.TouchAndInputWithComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TouchAndInputWithComposeTheme {
                Scaffold (
                    topBar = {
                        TopAppBar(modifier = Modifier.fillMaxWidth(), title = { Text(text = "Touch and Inputs")})
                    },
                    content = {
                        LazyColumn(
                            modifier = Modifier.fillMaxWidth() ,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            content = {
                            item { Click() }
                        })
                    }
                )
            }
        }
    }
}

@Composable
fun Click() {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(all = 10.dp)
            .background(Color.Gray)
            .pointerInput("") {
                detectTapGestures(
                    onPress = {
                        Toast
                            .makeText(context, "normal press", Toast.LENGTH_LONG)
                            .show()
                    }, //on press
                    onDoubleTap = {
                        Toast
                            .makeText(context, "double tap", Toast.LENGTH_LONG)
                            .show()
                    },
                    onLongPress = {
                        Toast
                            .makeText(context, "long press", Toast.LENGTH_LONG)
                            .show()
                    },
                    onTap = {
                        Toast
                            .makeText(context, "single tap", Toast.LENGTH_LONG)
                            .show()
                    } // when finger press is released
                )
            }
    ){
        Text(text = "perform" , modifier = Modifier.fillMaxSize() , textAlign = TextAlign.Center )
    }
}
