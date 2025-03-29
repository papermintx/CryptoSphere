package com.mk.cryptosphere.utils

import android.content.Context
import android.net.Uri
import java.io.BufferedReader
import java.io.InputStreamReader

fun readFileContent(context: Context, uri: Uri): String {
    val contentResolver = context.contentResolver
    val inputStream = contentResolver.openInputStream(uri)
    val reader = BufferedReader(InputStreamReader(inputStream))
    val stringBuilder = StringBuilder()
    var line: String?

    while (reader.readLine().also { line = it } != null) {
        stringBuilder.append(line).append("\n")
    }

    return stringBuilder.toString()
}