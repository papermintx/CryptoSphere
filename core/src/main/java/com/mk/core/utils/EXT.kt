package com.mk.core.utils

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

fun Uri.toFile(context: Context): File {
    val fileName = getFileName(context)
    val tempFile = File(context.cacheDir, fileName)

    try {
        context.contentResolver.openInputStream(this)?.use { inputStream ->
            FileOutputStream(tempFile).use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }

    return tempFile
}

fun Uri.getFileName(context: Context): String {
    var name = "tempFile"
    context.contentResolver.query(this, null, null, null, null)?.use { cursor ->
        val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        if (cursor.moveToFirst() && nameIndex >= 0) {
            name = cursor.getString(nameIndex)
        }
    }
    return name
}
