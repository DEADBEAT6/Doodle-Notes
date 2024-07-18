package com.raj.mywishlist

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raj.mywishlist.data.Wish
import com.raj.mywishlist.data.WishRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class WishViewModel(
    private val wishRepository: WishRepository = Graph.wishRepository
) : ViewModel() {

    var wishTitleState by mutableStateOf("")
    var wishDescriptionState by mutableStateOf("")
    var wishDrawingState by mutableStateOf("")
    var wishdrawlist by mutableStateOf<List<List<Point>>>(emptyList())

    lateinit var getAllWishes: Flow<List<Wish>>

    init {
        viewModelScope.launch {
            getAllWishes = wishRepository.getWishes()

        }
    }

    fun addDrawing(lineList: List<Point>) {
        wishdrawlist = wishdrawlist.toMutableList().apply {
            add(lineList.toList())
            Log.d("added", wishdrawlist.toString())
        }
        updateDrawingState()
    }

    // Function to remove the last drawing from the wishdrawlist
    fun removeLastDrawing() {
        if (wishdrawlist.isNotEmpty()) {
            wishdrawlist = wishdrawlist.toMutableList().apply {
                removeAt(lastIndex)
                Log.d("Undo", wishdrawlist.toString())
            }
            updateDrawingState()
        }
    }

    // Function to update the drawing state when the drawing changes
    private fun updateDrawingState() {
        wishDrawingState = convertListToJsonString(wishdrawlist)
    }

    fun onWishTitleChange(newString: String) {
        wishTitleState = newString
    }

    fun onWishDescriptionChange(newString: String) {
        wishDescriptionState = newString
    }

    fun addWish(wish: Wish) {
        viewModelScope.launch(Dispatchers.IO) {
            wishRepository.addAWish(wish = wish)
        }
    }

    fun updateWish(wish: Wish) {
        viewModelScope.launch(Dispatchers.IO) {
            wishRepository.updateAWish(wish = wish)
        }
    }

    fun deleteWish(wish: Wish) {
        viewModelScope.launch(Dispatchers.IO) {
            wishRepository.deleteAWish(wish = wish)
        }
    }

    fun getAWishById(id: Long): Flow<Wish> {
        return wishRepository.getAWishById(id)
    }

}

