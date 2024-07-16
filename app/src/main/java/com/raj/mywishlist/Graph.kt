package com.raj.mywishlist

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import com.raj.mywishlist.data.Wish
import com.raj.mywishlist.data.WishDatabase
import com.raj.mywishlist.data.WishRepository

object Graph {
    lateinit var database: WishDatabase


    val wishRepository by lazy {
        WishRepository(wishDao = database.wishDao())

    }
    fun provide(context: Context){
        database=Room.databaseBuilder(
            context,
            WishDatabase::class.java, "wishlist.db"
        ).build()
    }
}