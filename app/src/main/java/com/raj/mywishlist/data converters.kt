package com.raj.mywishlist

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun convertListToJsonString(lines: List<List<Point>>): String {
    val gson = Gson()
    return gson.toJson(lines)
}


fun convertJsonStringToList(jsonString: String): List<List<Point>> {
    val gson = Gson()
    val type = object : TypeToken<List<List<Point>>>() {}.type
    val result = gson.fromJson<List<List<Point>>>(jsonString, type) // Explicit type
    return result ?: emptyList() // Handle null result
}