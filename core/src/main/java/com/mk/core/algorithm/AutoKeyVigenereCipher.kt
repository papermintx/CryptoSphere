package com.mk.core.algorithm

object AutoKeyVigenereCipher {

    private const val ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

    fun encrypt(plaintext: String, key: String): String {
        val fullKey = generateFullKey(plaintext, key)
        val ciphertext = StringBuilder()

        for (i in plaintext.indices) {
            val plainChar = plaintext[i].uppercaseChar()
            val keyChar = fullKey[i].uppercaseChar()
            if (plainChar in ALPHABET) {
                val newIndex = (ALPHABET.indexOf(plainChar) + ALPHABET.indexOf(keyChar)) % ALPHABET.length
                ciphertext.append(ALPHABET[newIndex])
            } else {
                ciphertext.append(plainChar)
            }
        }
        return ciphertext.toString()
    }

    fun decrypt(ciphertext: String, key: String): String {
        val plaintext = StringBuilder()
        var fullKey = key

        for (i in ciphertext.indices) {
            val cipherChar = ciphertext[i].uppercaseChar()
            val keyChar = fullKey[i].uppercaseChar()
            if (cipherChar in ALPHABET) {
                val newIndex = (ALPHABET.indexOf(cipherChar) - ALPHABET.indexOf(keyChar) + ALPHABET.length) % ALPHABET.length
                val plainChar = ALPHABET[newIndex]
                plaintext.append(plainChar)
                fullKey += plainChar
            } else {
                plaintext.append(cipherChar)
            }
        }
        return plaintext.toString()
    }

    private fun generateFullKey(plaintext: String, key: String): String {
        val fullKey = StringBuilder(key)
        var keyIndex = 0

        for (i in key.length until plaintext.length) {
            fullKey.append(plaintext[keyIndex].uppercaseChar())
            keyIndex++
        }
        return fullKey.toString()
    }
}
