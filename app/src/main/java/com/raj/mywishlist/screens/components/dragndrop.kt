package com.raj.mywishlist.screens.components

import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.raj.mywishlist.R
import com.raj.mywishlist.WishViewModel
import com.raj.mywishlist.data.Wish
import com.raj.mywishlist.navigation.Screen
import com.raj.mywishlist.screens.WishItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun TwoColumnGridScreen(
    wishList: List<Wish>,
    navController: NavController,
    viewModel: WishViewModel,
    padding: PaddingValues
) {
    var deleteBoxBounds by remember { mutableStateOf<Rect?>(null) }
    var isDragging by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var itemBounds by remember { mutableStateOf<Rect?>(null) }
    var delete by remember { mutableStateOf(Color.LightGray) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        if (wishList.isNotEmpty()) {
            LazyVerticalStaggeredGrid(
                state = LazyStaggeredGridState(),
                columns = StaggeredGridCells.Fixed(2),
                verticalItemSpacing = 4.dp,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                content = {
                    items(wishList, key = { wish -> wish.id }) { wish ->
                        var offset by remember { mutableStateOf(Offset(0f, 0f)) }
                        val initialOffset = remember { mutableStateOf(Offset(0f, 0f)) }
                        var isHeld by remember { mutableStateOf(false) }
                        var isCollided by remember { mutableStateOf(false) }


                        Box(
                            modifier = Modifier
                                .offset { IntOffset(offset.x.roundToInt(), offset.y.roundToInt()) }
                                .pointerInput(Unit) {
                                    detectDragGesturesAfterLongPress(
                                        onDragStart = {
                                            isHeld = true
                                            CoroutineScope(Dispatchers.Main).launch {
                                                // Delay for hold duration
                                                if (isHeld) {
                                                    triggerVibrationLong(context)
                                                    isDragging = true
                                                    initialOffset.value = offset
                                                }

                                            }
                                        },
                                        onDragEnd = {
                                            if (deleteBoxBounds != null && itemBounds!!.overlaps(
                                                    deleteBoxBounds!!
                                                ) && isHeld
                                            ) {
                                                viewModel.deleteWish(wish)
                                                playSound(context, R.raw.delete_sound)
                                                isDragging = false
                                            }
                                            isCollided = false
                                            isHeld = false
                                            isDragging = false
                                            offset = initialOffset.value
                                        },
                                        onDragCancel = {
                                            isHeld = false
                                            isDragging = false
                                            isCollided = false
                                            offset = initialOffset.value
                                        }
                                    ) { change, dragAmount ->
                                        if (isDragging) {
                                            change.consume()
                                            offset += dragAmount
                                        }
                                        if (deleteBoxBounds != null && itemBounds!!.overlaps(
                                                deleteBoxBounds!!
                                            ) && isHeld && !isCollided
                                        ) {
                                            isCollided = true
                                            triggerVibration(context)
                                            delete = Color.Red
                                        }
                                        if (deleteBoxBounds != null && itemBounds!!.overlaps(
                                                deleteBoxBounds!!
                                            )
                                        ) {
                                            delete = Color.Red
                                        } else {
                                            delete = Color.LightGray
                                        }
                                    }
                                }

                                .onGloballyPositioned { coordinates ->
                                    itemBounds = coordinates.boundsInRoot()


                                }
                        )  {
                            WishItem(wish = wish) {
                                val id = wish.id
                                triggerVibration(context)
                                navController.navigate(Screen.AddScreen.route + "/$id")
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxSize()
            )

            if (isDragging) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(20.dp)
                        .height(75.dp)
                        .width(75.dp)
                        .background(delete, CircleShape)
                        .onGloballyPositioned { coordinates ->
                            deleteBoxBounds = coordinates.boundsInRoot()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Icon",
                        tint = Color.White
                    )
                }
            }
        }
        if (wishList.isEmpty()) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = "No Notes Exist",
                    style = TextStyle(
                        fontSize = 20.sp
                    )
                )
            }
        }
    }
}


fun triggerVibration(context: Context) {
    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
    } else {
        vibrator.vibrate(200)
    }
}

fun triggerVibrationLong(context: Context) {
    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE))
    } else {
        vibrator.vibrate(200)
    }
}

fun playSound(context: Context, soundResId: Int) {
    val mediaPlayer = MediaPlayer.create(context, soundResId)
    mediaPlayer.setOnCompletionListener {
        it.release() // Release the media player resource after playing
    }
    mediaPlayer.start() // Start playing the sound
}
