package com.mk.core.algorithm

import android.util.Log

object HillCipher {

    private const val ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

    private fun isKeyInvertible(matrix: Array<IntArray>): Boolean {
        val determinant = (matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0]) % ALPHABET.length
        return determinant != 0
    }


    private fun textToNumbers(text: String): IntArray {
        return text.uppercase().map { ALPHABET.indexOf(it) }.toIntArray()
    }

    private fun numbersToText(numbers: IntArray): String {
        return numbers.joinToString("") { ALPHABET[it % ALPHABET.length].toString() }
    }

    fun encrypt(plaintext: String, key: Array<IntArray>): String {
        if (!isKeyInvertible(key)) {
            throw IllegalArgumentException("The key matrix is not invertible. Please choose a valid key.")
        }

        val plaintextNumbers = textToNumbers(plaintext)
        val ciphertext = StringBuilder()

        // Splitting plaintext into 2-sized blocks (since we are using a 2x2 matrix)
        for (i in plaintextNumbers.indices step 2) {
            val block = intArrayOf(plaintextNumbers[i], if (i + 1 < plaintextNumbers.size) plaintextNumbers[i + 1] else 0)
            val encryptedBlock = encryptBlock(block, key)
            ciphertext.append(numbersToText(encryptedBlock))
        }

        return ciphertext.toString()
    }

    private fun encryptBlock(block: IntArray, key: Array<IntArray>): IntArray {
        val encryptedBlock = IntArray(block.size)
        for (i in block.indices) {
            encryptedBlock[i] = (key[i][0] * block[0] + key[i][1] * block[1]) % ALPHABET.length
        }
        return encryptedBlock
    }




    fun decrypt(ciphertext: String, key: Array<IntArray>): String {
        if (!isKeyInvertible(key)) {
            throw IllegalArgumentException("The key matrix is not invertible. Please choose a valid key.")
        }

        val invertedKey = invertKey(key)

        val ciphertextNumbers = textToNumbers(ciphertext)
        val plaintext = StringBuilder()

        for (i in ciphertextNumbers.indices step 2) {
            val block = intArrayOf(ciphertextNumbers[i], if (i + 1 < ciphertextNumbers.size) ciphertextNumbers[i + 1] else 0)
            val decryptedBlock = decryptBlock(block, invertedKey)
            Log.d("HillCipher", "Decrypted block: ${decryptedBlock.joinToString(", ")}")

            plaintext.append(numbersToText(decryptedBlock))
        }

        return plaintext.toString()
    }

    private fun decryptBlock(block: IntArray, invertedKey: Array<IntArray>): IntArray {
        val decryptedBlock = IntArray(block.size)
        for (i in block.indices) {
            decryptedBlock[i] = (invertedKey[i][0] * block[0] + invertedKey[i][1] * block[1]) % ALPHABET.length
            if (decryptedBlock[i] < 0) {
                decryptedBlock[i] += ALPHABET.length  // Pastikan nilai positif
            }
        }
        return decryptedBlock
    }





    private fun invertKey(key: Array<IntArray>): Array<IntArray> {
        val determinant = (key[0][0] * key[1][1] - key[0][1] * key[1][0]) % ALPHABET.length
        Log.d("HillCipher", "Determinant: $determinant")

        if (determinant == 0) {
            Log.e("HillCipher", "The key matrix cannot be inverted! Determinant is 0.")
            throw IllegalArgumentException("The key matrix cannot be inverted!")
        }

        val determinantInverse = modInverse(determinant, ALPHABET.length)
        Log.d("HillCipher", "Determinant Inverse: $determinantInverse")

        if (determinantInverse == -1) {
            Log.e("HillCipher", "The key matrix cannot be inverted! No modular inverse found.")
            throw IllegalArgumentException("The key matrix cannot be inverted! No modular inverse found.")
        }

        // Log the key matrix before returning inverted matrix
        Log.d("HillCipher", "Inverted Key: ${key.joinToString(", ") { it.joinToString(", ") }}")

        return arrayOf(
            intArrayOf(
                (key[1][1] * determinantInverse) % ALPHABET.length,
                (-key[0][1] * determinantInverse) % ALPHABET.length
            ),
            intArrayOf(
                (-key[1][0] * determinantInverse) % ALPHABET.length,
                (key[0][0] * determinantInverse) % ALPHABET.length
            )
        )
    }




    private fun modInverse(a: Int, n: Int): Int {
        for (i in 1 until n) {
            if ((a * i) % n == 1) return i
        }
        return -1
    }
}
