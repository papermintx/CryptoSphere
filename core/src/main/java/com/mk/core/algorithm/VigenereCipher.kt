package com.mk.core.algorithm


object VigenereCipher {

    private const val ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

    private fun normalizePlaintext(plaintext: String): String {
        return plaintext.filter { it.isLetter() }
    }

    fun encrypt(plaintext: String, key: String): String {
        val normalizedPlaintext = normalizePlaintext(plaintext)
        val ciphertext = StringBuilder()

        var keyIndex = 0
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

            keyIndex++
        }

        return formatCiphertext(ciphertext.toString())
    }

    fun decrypt(ciphertext: String, key: String): String {
        val normalizedCiphertext = normalizePlaintext(ciphertext)
        val plaintext = StringBuilder()

        var keyIndex = 0
        for (char in normalizedCiphertext) {
            val isLower = char.isLowerCase()
            val ciphertextIndex = ALPHABET.indexOf(char.uppercaseChar())
            val keyChar = key[keyIndex % key.length]
            val keyIndexInAlphabet = ALPHABET.indexOf(keyChar.uppercaseChar())
            val plaintextIndex = (ciphertextIndex - keyIndexInAlphabet + ALPHABET.length) % ALPHABET.length

            if (isLower) {
                plaintext.append(ALPHABET[plaintextIndex].lowercaseChar())
            } else {
                plaintext.append(ALPHABET[plaintextIndex].uppercaseChar())
            }

            keyIndex++
        }

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
