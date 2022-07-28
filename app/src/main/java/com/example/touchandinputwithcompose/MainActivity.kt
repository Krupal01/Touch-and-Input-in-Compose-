package com.example.touchandinputwithcompose

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.touchandinputwithcompose.ui.theme.TouchAndInputWithComposeTheme
const val TAG = "tag"
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
                                item { DragByDraggable() }
                                item { DragByPointerInput() }
                                item { Scrollable() }
                                item { Swipeable() }
                                item { Transformable() }
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

@Composable
fun DragByDraggable() {
    var value by remember { mutableStateOf(0f) }
    val state = rememberDraggableState(onDelta = {
        value = it
    })

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 10.dp)
            .background(Color.Gray)
            .draggable(
                state = state,
                orientation = Orientation.Horizontal,
                onDragStarted = { Log.i(TAG, "drag started") },
                onDragStopped = { Log.i(TAG, "drag stopped") }
            )
    ){
        Text(
            text = "Drag $value",
            modifier = Modifier
                .width(100.dp)
                .background(Color.White)
        )
    }
}

@Composable
fun DragByPointerInput() {
    var x by remember{ mutableStateOf(0f)}
    var y by remember{ mutableStateOf(0f)}

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .padding(all = 10.dp)
            .background(Color.Gray)
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    x = dragAmount.x
                    y = dragAmount.y
                }
            }
    ){
        Text(
            text = "Drag X:$x , Y:$y",
            modifier = Modifier
                .width(100.dp)
                .background(Color.White)
        )
    }
}

@Composable
fun Scrollable() {
    var value by remember{ mutableStateOf(0f)}
    val scrollableState = rememberScrollableState(
        consumeScrollDelta = {
                delta -> value = delta
                0f
        }
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(all = 10.dp)
            .background(Color.Gray)
    ){
        Text(
            text = "Scrolled $value " ,
            modifier = Modifier
                .scrollable(
                    state = scrollableState,
                    orientation = Orientation.Vertical
                ),
            textAlign = TextAlign.Center ,
            fontSize = 20.sp
        )
    }
}

enum class States { LEFT, RIGHT }
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Swipeable() {
    val width = 300.dp
    val squareSize = 100.dp
    val swipeableState = rememberSwipeableState(States.LEFT)
    val squareSizePx = with(LocalDensity.current) {
        (width-squareSize).toPx()
    }
    Box(
        modifier = Modifier
            .width(width)
            .swipeable(
                state = swipeableState,
                anchors = mapOf(0f to States.LEFT, squareSizePx to States.RIGHT),
                thresholds = { _, _ -> FractionalThreshold(0.5f) },
                orientation = Orientation.Horizontal
            )
            .background(Color.LightGray)
    ) {
        Box(
            modifier = Modifier
                .offset {
                    IntOffset(swipeableState.offset.value.toInt(), 0)
                }
                .size(squareSize)
                .background(Color.DarkGray)
        )
    }
}

@Composable
fun Transformable() {
    var scale by remember { mutableStateOf(1f) }
    var rotation by remember { mutableStateOf(0f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    val state = rememberTransformableState {
            zoomChange, offsetChange, rotationChange ->
        scale *= zoomChange
        rotation += rotationChange
        offset += offsetChange
    }

    Box(modifier = Modifier.fillMaxWidth().height(500.dp)) {
        Box(
            modifier = Modifier
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                    rotationZ = rotation,
                    translationX = offset.x,
                    translationY = offset.y
                )
                .transformable(state = state)
                .background(Color.Blue)
                .fillMaxSize()
        )
    }


}