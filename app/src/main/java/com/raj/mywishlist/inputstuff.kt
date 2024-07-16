package com.raj.mywishlist

import android.util.Log
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

data class Point(
    val start: Offset, val end: Offset
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Drawing(viewModel: WishViewModel, modifier: Modifier) {
    val lineList = remember { mutableStateListOf<Point>() }
    var isDrawingMode by remember { mutableStateOf(false) }



    Column(modifier) {
        Row {
            Button(onClick = {
                viewModel.wishdrawlist = emptyList()
            }) {
                Text(text = "Clear")
            }
            Button(onClick = {
                if (viewModel.wishdrawlist.isNotEmpty()) {
                    viewModel.removeLastDrawing()
                }
            }) {
                Text(text = "Undo")
            }
            Button(onClick = {
                isDrawingMode = !isDrawingMode
            }) {
                Text(text = if (!isDrawingMode) "Text Mode" else "Drawing Mode")
            }
        }

        Box(modifier = Modifier
            .weight(1f)
            .fillMaxSize()
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragEnd = {
                        if (lineList.isNotEmpty()) {
                            viewModel.addDrawing(lineList)
                            Log.d("LineList added to history", lineList.toString())
                        }
                    },
                    onDragStart = {lineList.clear()},
                )
                { change, dragAmount ->
                    if (isDrawingMode) {
                        val end = change.position
                        val start = end - dragAmount
                        lineList.add(Point(start, end))
                        Log.d("Line added to list", lineList.toString())
                    }
                }
            }
            .drawBehind {
                viewModel.wishdrawlist.forEach { lineList ->
                    lineList.forEach { point ->
                        drawLine(
                            color = Color.Red,
                            start = point.start,
                            end = point.end,
                            strokeWidth = 4.dp.toPx()
                        )
                    }
                }

                // Draw the current line list
                lineList.forEach { point ->
                    drawLine(
                        color = Color.Red,
                        start = point.start,
                        end = point.end,
                        strokeWidth = 4.dp.toPx()
                    )
                }
            }) {
            if (!isDrawingMode) {
                OutlinedTextField(modifier = Modifier.fillMaxSize(),
                    textStyle = TextStyle(color = Color.Black),
                    colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent),
                    value = viewModel.wishDescriptionState,
                    onValueChange = { viewModel.onWishDescriptionChange(it) })
            } else {
                Text(
                    text = viewModel.wishDescriptionState, modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}


