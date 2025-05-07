package com.mk.core.algorithm

import android.util.Log


object VigenereCipher {

    const val TAG = "VigenereCipher"

    private const val ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

    private fun normalizePlaintext(plaintext: String): String {
        val normalized = plaintext.filter { it.isLetter() }
        Log.d(TAG, "normalizePlaintext: Normalized plaintext -> $normalized")
        return normalized
    }

    fun encrypt(plaintext: String, key: String): String {
        val normalizedPlaintext = normalizePlaintext(plaintext)
        val ciphertext = StringBuilder()
        var keyIndex = 0

        Log.d(TAG, "encrypt: Starting encryption")

        for (char in normalizedPlaintext) {
            val isLower = char.isLowerCase()
            val plaintextIndex = ALPHABET.indexOf(char.uppercaseChar())
            val keyChar = key[keyIndex % key.length]
            val keyIndexInAlphabet = ALPHABET.indexOf(keyChar.uppercaseChar())
            val ciphertextIndex = (plaintextIndex + keyIndexInAlphabet) % ALPHABET.length

            if (isLower) {
                ciphertext.append(ALPHABET[ciphertextIndex].lowercaseChar())
            } else {
                ciphertext.append(ALPHABET[ciphertextIndex].uppercaseChar())
            }

            Log.d(TAG, "encrypt: Char $char -> Ciphertext: ${ciphertext.last()}")

            keyIndex++
        }

        val formattedCiphertext = formatCiphertext(ciphertext.toString())
        Log.d(TAG, "encrypt: Final ciphertext: $formattedCiphertext")

        return formattedCiphertext
    }

    fun decrypt(ciphertext: String, key: String): String {
        val normalizedCiphertext = normalizePlaintext(ciphertext)
        val plaintext = StringBuilder()
        var keyIndex = 0

        Log.d(TAG, "decrypt: Starting decryption")

        for (char in normalizedCiphertext) {
            // Check if the character is valid (i.e., present in the ALPHABET string)
            val ciphertextIndex = ALPHABET.indexOf(char.uppercaseChar())
            if (ciphertextIndex == -1) {
                Log.e(TAG, "decrypt: Invalid character in ciphertext: $char")
                throw IllegalArgumentException("Invalid character in ciphertext: $char")
            }

            val keyChar = key[keyIndex % key.length]
            val keyIndexInAlphabet = ALPHABET.indexOf(keyChar.uppercaseChar())
            if (keyIndexInAlphabet == -1) {
                Log.e(TAG, "decrypt: Invalid character in key: $keyChar")
                throw IllegalArgumentException("Invalid character in key: $keyChar")
            }

            val isLower = char.isLowerCase()
            val plaintextIndex = (ciphertextIndex - keyIndexInAlphabet + ALPHABET.length) % ALPHABET.length

            if (isLower) {
                plaintext.append(ALPHABET[plaintextIndex].lowercaseChar())
            } else {
                plaintext.append(ALPHABET[plaintextIndex].uppercaseChar())
            }

            Log.d(TAG, "decrypt: Char $char -> Plaintext: ${plaintext.last()}")

            keyIndex++
        }

        Log.d(TAG, "decrypt: Final plaintext: $plaintext")

        return plaintext.toString()
    }

    private fun formatCiphertext(ciphertext: String): String {
        val groupedCiphertext = StringBuilder()
        var counter = 0

        for (char in ciphertext) {
            groupedCiphertext.append(char)
            counter++
            if (counter % 5 == 0) {
                groupedCiphertext.append(" ")
            }
        }

        return groupedCiphertext.toString().trim()
    }
}
