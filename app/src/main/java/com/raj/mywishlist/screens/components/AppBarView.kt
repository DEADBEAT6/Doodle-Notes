package com.raj.mywishlist.screens.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.raj.mywishlist.R

@Composable
fun AppBarView(
    title: String,
    onBackNavClicked: ()-> Unit = {},

){

    val navigationIcon : (@Composable () ->Unit)? = {
        if (!title.contains("Doodle Notes")){
            IconButton(onClick = { onBackNavClicked() } ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }else {
                Image(
                    painter = painterResource(id = R.mipmap.mainpic), // Replace with your icon resource
                    contentDescription = null,
                    Modifier
                        .size(45.dp).
                        padding(start = 10.dp),

                )

        }
    }
    TopAppBar (
        title = {
            Text(
                text = title,
                color = colorResource(id = R.color.white),
                modifier = Modifier
                    .padding(start = 4.dp)
                    .heightIn(max = 24.dp))},
        elevation = 3.dp,
        backgroundColor = colorResource(id = R.color.app_bar_color),
        navigationIcon = navigationIcon
    )
}