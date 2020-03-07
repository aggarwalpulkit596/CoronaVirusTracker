package com.puldroid.coronavirustracker.utils

import android.content.Context
import android.util.Log
import org.json.JSONObject
import java.io.InputStream

fun Context.loadJsonObjectFromAsset(assetName: String): Any? {
    try {
        val json = loadStringFromAsset(this, assetName)
        return JSONObject(json)
    } catch (e: Exception) {
        Log.e("JsonUtils", e.toString())
    }
    return null
}

fun loadStringFromAsset(context: Context, assetName: String): String? {
    val `is`: InputStream = context.assets.open(assetName)
    val size: Int = `is`.available()
    val buffer = ByteArray(size)
    `is`.read(buffer)
    `is`.close()
    return String(buffer)
}