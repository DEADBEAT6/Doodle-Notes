package com.raj.mywishlist.screens.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.raj.mywishlist.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarView(
    title: String,
    searchText: String,
    onSearchTextChanged: (String) -> Unit = {},
    onActiveChange: (Boolean) -> Unit = {},
    onBackNavClicked: () -> Unit = {},
    showSearchBar: Boolean = true
) {
    val navigationIcon: (@Composable () -> Unit)? = {
        if (!title.contains("Doodle Notes")) {
            IconButton(onClick = { onBackNavClicked() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        } else {
            Image(
                painter = painterResource(id = R.mipmap.mainpic), // Replace with your icon resource
                contentDescription = null,
                Modifier
                    .size(45.dp)
                    .padding(start = 10.dp),
            )
        }
    }

    TopAppBar(
        modifier = Modifier.height(75.dp),
        title = {
            if (title.contains("Doodle Notes")) {
                Row(Modifier.padding(4.dp)) {

                    SearchBar(
                        modifier = Modifier,
                        query = searchText,
                        enabled = showSearchBar,
                        onQueryChange = onSearchTextChanged,
                        onSearch = {},
                        active = false,
                        onActiveChange = onActiveChange,
                        placeholder = {
                            Text(text = "Search")
                        },
                        leadingIcon = {
                            Icon(Icons.Default.Search, contentDescription = "Search")
                        },
                        content = { }
                    )

                }
            } else {
                Text(
                    text = title,
                    color = colorResource(id = R.color.white),
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .heightIn(max = 24.dp))
            }
        },
        elevation = 3.dp,
        backgroundColor = colorResource(id = R.color.app_bar_color),
        navigationIcon = navigationIcon,
    )
}
