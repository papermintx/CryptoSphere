package com.mk.cryptosphere.utils

import android.content.Context
import android.net.Uri

fun saveCiphertextToFile(context: Context, outputUri: Uri, ciphertext: String): Boolean {
    return try {
        context.contentResolver.openOutputStream(outputUri)?.use { outputStream ->
            outputStream.write(ciphertext.toByteArray())
        }
        true
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }
}
