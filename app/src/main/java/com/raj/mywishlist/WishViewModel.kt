package com.raj.mywishlist

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
    var wishDrawingState by mutableStateOf<ByteArray?>(null)
    var wishdrawlist by mutableStateOf<List<List<Point>>>(emptyList())

    fun addDrawing(lineList: List<Point>) {
        wishdrawlist = wishdrawlist.toMutableList().apply {
            add(lineList.toList())
        }
        updateDrawingState()
    }

    // Function to remove the last drawing from the wishdrawlist
    fun removeLastDrawing() {
        if (wishdrawlist.isNotEmpty()) {
            wishdrawlist = wishdrawlist.toMutableList().apply {
                removeAt(lastIndex)
            }
            updateDrawingState()
        }
    }

    // Function to update the drawing state when the drawing changes
    private fun updateDrawingState() {
        wishDrawingState = convertListToByteArray(wishdrawlist)
    }


    lateinit var getAllWishes: Flow<List<Wish>>
    lateinit var getAWishById: Flow<Wish>

    init {
        viewModelScope.launch {
            getAllWishes = wishRepository.getWishes()

        }
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

