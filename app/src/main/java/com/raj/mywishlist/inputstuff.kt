package com.raj.mywishlist

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.mohamedrejeb.richeditor.model.RichTextState
import com.raj.myapplication.MainScreen.RichTextInput


data class Point(
    val start: Offset, val end: Offset
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Drawing(viewModel: WishViewModel, modifier: Modifier, state: RichTextState, id: Long) {
    val lineList = remember { mutableStateListOf<Point>() }
    var isDrawingMode by remember { mutableStateOf(false) }

    val colors = listOf(
        colorResource(id = R.color.card_color1),
        colorResource(id = R.color.card_color2),
        colorResource(id = R.color.card_color3),
        colorResource(id = R.color.card_color4),
        colorResource(id = R.color.card_color5),

        )
    var personalColor: Int
    personalColor = if (id == 0L) {
        1
    } else {
        id.toInt() % colors.size
    }


    // Select a random color
    val color = colors[personalColor]
    Column(modifier) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Row {
                if (isDrawingMode) {
                    Button(onClick = {
                        viewModel.wishdrawlist = emptyList()
                        viewModel.wishDrawingState = ""
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
                }
            }
            Button(
                onClick = {
                    if (isDrawingMode) {
                        state.setHtml(viewModel.wishDescriptionState)
                        viewModel.onWishDescriptionChange(state.toHtml())
                    } else {
                        viewModel.onWishDescriptionChange(state.toHtml())
                        state.setHtml(viewModel.wishDescriptionState)
                    }
                    isDrawingMode = !isDrawingMode

                }) {
                Row(modifier = Modifier.height(20.dp).width(100.dp),
                    horizontalArrangement = Arrangement.Center) {
                    Text(text = if (!isDrawingMode) "Text Mode" else "Draw   Mode")
                }
            }
        }

        Box(modifier = Modifier
            .weight(1f)
            .fillMaxSize()
            .background(color)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragEnd = {
                        if (lineList.isNotEmpty()) {
                            viewModel.addDrawing(lineList)
                            lineList.clear()
                            Log.d("LineList added to viewmodel", lineList.toString())
                        }
                    },
                    onDragStart = { lineList.clear() },
                ) { change, dragAmount ->
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
            Column {
                if (isDrawingMode) {
                    RichTextInput(viewModel, state, !isDrawingMode)
                } else {
                    RichTextInput(viewModel, state, !isDrawingMode)
                }

            }


        }
    }
}


