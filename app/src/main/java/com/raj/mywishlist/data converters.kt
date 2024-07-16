package com.raj.mywishlist

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun convertListToByteArray(lines: List<List<Point>>): ByteArray {
    val gson = Gson()
    val jsonString = gson.toJson(lines)
    return jsonString.toByteArray()
}

fun convertByteArrayToList(byteArray: ByteArray): List<List<Point>> {
    val jsonString = String(byteArray)
    val gson = Gson()
    val type = object : TypeToken<List<List<Point>>>() {}.type
    return gson.fromJson(jsonString, type)
}