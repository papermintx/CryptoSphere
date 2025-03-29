package com.mk.core.algorithm

object VigenereCipher {
    private const val ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

    private fun normalizePlaintext(plaintext: String): String {
        return plaintext.uppercase().filter { it in ALPHABET }
    }

    fun encrypt(plaintext: String, key: String): String {
        val normalizedPlaintext = normalizePlaintext(plaintext)
        val ciphertext = StringBuilder()

        var keyIndex = 0
        for (char in normalizedPlaintext) {
            val plaintextIndex = ALPHABET.indexOf(char)
            val keyChar = key[keyIndex % key.length]
            val keyIndexInAlphabet = ALPHABET.indexOf(keyChar.uppercaseChar())
            val ciphertextIndex = (plaintextIndex + keyIndexInAlphabet) % ALPHABET.length
            ciphertext.append(ALPHABET[ciphertextIndex])
            keyIndex++
        }

        return ciphertext.toString()
    }

    fun decrypt(ciphertext: String, key: String): String {
        val normalizedCiphertext = normalizePlaintext(ciphertext)
        val plaintext = StringBuilder()

        var keyIndex = 0
        for (char in normalizedCiphertext) {
            val ciphertextIndex = ALPHABET.indexOf(char)
            val keyChar = key[keyIndex % key.length]
            val keyIndexInAlphabet = ALPHABET.indexOf(keyChar.uppercaseChar())
            val plaintextIndex = (ciphertextIndex - keyIndexInAlphabet + ALPHABET.length) % ALPHABET.length
            plaintext.append(ALPHABET[plaintextIndex])
            keyIndex++
        }

        return plaintext.toString()
    }
}