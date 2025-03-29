package com.mk.core.algorithm

import android.content.Context
import android.net.Uri
import java.io.InputStream
import java.io.OutputStream

object ExtendedVigenereCipher {
    private  fun encrypt(plaintext: ByteArray, key: String): ByteArray {
        val keyBytes = key.toByteArray()
        val ciphertext = ByteArray(plaintext.size)
        for (i in plaintext.indices) {
            ciphertext[i] = (plaintext[i].toInt() + keyBytes[i % keyBytes.size].toInt()).toByte()
        }
        return ciphertext
    }

    private  fun decrypt(ciphertext: ByteArray, key: String): ByteArray {
        val keyBytes = key.toByteArray()
        val plaintext = ByteArray(ciphertext.size)
        for (i in ciphertext.indices) {
            plaintext[i] = (ciphertext[i].toInt() - keyBytes[i % keyBytes.size].toInt()).toByte()
        }
        return plaintext
    }

    fun encryptFileAndOverwrite(context: Context, inputUri: Uri, key: String): Boolean {
        try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(inputUri)
            if (inputStream != null) {
                val plaintext = inputStream.readBytes()
                inputStream.close()

                val ciphertext = encrypt(plaintext, key)

                val outputStream: OutputStream? = context.contentResolver.openOutputStream(inputUri)
                if (outputStream != null) {
                    outputStream.write(ciphertext)
                    outputStream.close()
                }
                return true
            } else {
                return false
            }
        } catch (e: Exception) {
            throw  e
        }
    }

    fun decryptFileAndOverwrite(context: Context, inputUri: Uri, key: String): Boolean {
       try {
           val inputStream: InputStream? = context.contentResolver.openInputStream(inputUri)
           if (inputStream != null) {
               val ciphertext = inputStream.readBytes()
               inputStream.close()

               val plaintext = decrypt(ciphertext, key)

               val outputStream: OutputStream? = context.contentResolver.openOutputStream(inputUri)
               if (outputStream != null) {
                   outputStream.write(plaintext)
                   outputStream.close()
               }
               return true
           } else {
               return false
           }
       } catch (e: Exception) {
           throw e
       }
    }
}
