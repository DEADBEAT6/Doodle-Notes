package com.raj.mywishlist.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditor
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditorDefaults
import com.raj.mywishlist.R
import com.raj.mywishlist.WishViewModel
import com.raj.mywishlist.data.Wish
import com.raj.mywishlist.navigation.Screen
import com.raj.mywishlist.screens.components.AppBarView
import com.raj.mywishlist.screens.components.TwoColumnGridScreen


@Composable
fun HomeView(
    navController: NavController,
    viewModel: WishViewModel
) {

    Scaffold(
        topBar = {
            AppBarView(
                "Doodle Notes",
                onBackNavClicked = {
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(modifier = Modifier.padding(all = 20.dp),
                backgroundColor = Color.Black,

                onClick = { //add navigation to home screen/
                    navController.navigate(Screen.AddScreen.route + "/0L")

                }) {
                Icon(
                    painter = painterResource(id = R.drawable.sharp_heart_broken_24),
                    contentDescription = null,
                    tint = Color.Red
                )
            }
        }

    )
    {
        val wishList by viewModel.getAllWishes.collectAsState(initial = emptyList())

        TwoColumnGridScreen(
            wishList = wishList,
            navController = navController,
            viewModel = viewModel,
            padding = it
        )


    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WishItem(wish: Wish, onClick: () -> Unit) {
    val state = rememberRichTextState()
    state.setHtml(wish.description)
    val colors = listOf(
        colorResource(id = R.color.card_color1),
        colorResource(id = R.color.card_color2),
        colorResource(id = R.color.card_color3),
        colorResource(id = R.color.card_color4),
        colorResource(id = R.color.card_color5),

        )
    val personalColor = wish.id.toInt() % colors.size
    // Select a random color
    val color = colors[personalColor]
    Card(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            // .height(100.dp)
            // .width(100.dp)

            .padding(top = 4.dp, start = 8.dp, end = 8.dp, bottom = 4.dp)
            .clickable {
                onClick()
            },
        elevation = 10.dp,
        backgroundColor = color
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .padding(start = 5.dp)
        ) {
            Text(text = wish.title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            val a = 10
            RichTextEditor(
                modifier = Modifier.fillMaxWidth(),
                state = state,
                textStyle = TextStyle(Color.Black),
                colors = RichTextEditorDefaults.richTextEditorColors(containerColor = Color.Transparent),
                enabled = false,
                contentPadding = RichTextEditorDefaults.richTextEditorWithoutLabelPadding(
                    start = 4.dp,
                    end = a.dp,
                    top = a.dp,
                    bottom = a.dp
                )

            )

        }
    }

}

//
//
//@Composable
//fun TwoColumnGridScreen1(
//    wishLis: List<Wish>,
//    navController: NavController,
//    viewModel: WishViewModel,
//    padding: PaddingValues
//) {
//    Column {
//
//
//        if (wishLis.isNotEmpty()) {
//            LazyVerticalStaggeredGrid(
//                state = LazyStaggeredGridState(),
//                columns = StaggeredGridCells.Fixed(2),
//                verticalItemSpacing = 4.dp,
//                horizontalArrangement = Arrangement.spacedBy(4.dp),
//                content = {
//                    items(wishLis, key = { wish -> wish.id }) { wish ->
//                        val dismissState = rememberDismissState(
//                            confirmStateChange = { dismissValue ->
//                                if (dismissValue == DismissValue.DismissedToEnd || dismissValue == DismissValue.DismissedToStart) {
//                                    viewModel.deleteWish(wish)
//                                }
//                                true
//                            }
//                        )
//
//                        SwipeToDismiss(
//                            state = dismissState,
//                            background = {
//                                val dismissDirection =
//                                    dismissState.dismissDirection ?: return@SwipeToDismiss
//                                val color = animateColorAsState(
//                                    targetValue = if (dismissDirection == DismissDirection.EndToStart ||
//                                        dismissDirection == DismissDirection.StartToEnd
//                                    ) Color.Red else Color.Transparent
//                                ).value
//
//                                Box(
//                                    modifier = Modifier
//                                        .fillMaxSize()
//                                        .background(color)
//                                        .padding(20.dp),
//                                    contentAlignment = Alignment.Center
//                                ) {
//                                    Icon(
//                                        imageVector = Icons.Default.Delete,
//                                        contentDescription = "Delete Icon",
//                                        tint = Color.White
//                                    )
//                                }
//                            },
//                            directions = setOf(
//                                DismissDirection.EndToStart,
//                                DismissDirection.StartToEnd
//                            ),
//                            dismissThresholds = { FractionalThreshold(0.9f) },
//                            dismissContent = {
//                                WishItem(wish = wish) {
//                                    val id = wish.id
//                                    navController.navigate(Screen.AddScreen.route + "/$id")
//                                }
//                            }
//                        )
//
//                    }
//                },
//                modifier = Modifier
//                    .fillMaxSize()
//                    .weight(1f)
//                    .padding(padding),
//            )
//        } else {
//            Row(
//                horizontalArrangement = Arrangement.Center,
//                verticalAlignment = Alignment.CenterVertically,
//                modifier = Modifier.fillMaxSize()
//            ) {
//                Text(
//                    text = "No Notes Exist ",
//                    style = TextStyle(
//                        fontSize = 20.sp
//                    )
//                )
//            }
//        }
//    }
//}
//
//

