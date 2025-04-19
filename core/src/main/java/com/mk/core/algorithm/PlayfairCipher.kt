package com.mk.core.algorithm

object PlayfairCipher {
    private const val ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

    private fun prepareKey(key: String): Array<CharArray> {
        val keyString = (key.uppercase().filter { it in ALPHABET } + ALPHABET)
            .toSet().joinToString("")
        return Array(5) { row -> CharArray(5) { col -> keyString[row * 5 + col] } }
    }

    private fun prepareText(text: String): String {
        val cleanText = text.uppercase()
            .filter { it in ALPHABET }

        val result = StringBuilder()
        var i = 0
        while (i < cleanText.length) {
            val a = cleanText[i]
            val b = if (i + 1 < cleanText.length) cleanText[i + 1] else 'X'

            if (a == b) {
                result.append(a)
                result.append('X')
                i++
            } else {
                result.append(a)
                result.append(b)
                i += 2
            }
        }

        if (result.length % 2 != 0) result.append("X")

        return result.toString()
    }


    private fun findPosition(matrix: Array<CharArray>, char: Char): Pair<Int, Int> {
        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                if (matrix[i][j] == char) return i to j
            }
        }
        throw IllegalArgumentException("Character not found in matrix")
    }

    fun encrypt(plainText: String, key: String): String {
        val matrix = prepareKey(key)
        val preparedText = prepareText(plainText)
        val cipherText = StringBuilder()

        for (i in preparedText.indices step 2) {
            val (row1, col1) = findPosition(matrix, preparedText[i])
            val (row2, col2) = findPosition(matrix, preparedText[i + 1])

            when {
                row1 == row2 -> {
                    cipherText.append(matrix[row1][(col1 + 1) % 5])
                    cipherText.append(matrix[row2][(col2 + 1) % 5])
                }
                col1 == col2 -> {
                    cipherText.append(matrix[(row1 + 1) % 5][col1])
                    cipherText.append(matrix[(row2 + 1) % 5][col2])
                }
                else -> {
                    cipherText.append(matrix[row1][col2])
                    cipherText.append(matrix[row2][col1])
                }
            }
        }
        return cipherText.toString().uppercase()
    }

    fun decrypt(cipherText: String, key: String): String {
        val matrix = prepareKey(key)
        val cleanCipher = cipherText.uppercase().filter { it in ALPHABET }
        val plainText = StringBuilder()

        for (i in cleanCipher.indices step 2) {
            val (row1, col1) = findPosition(matrix, cleanCipher[i])
            val (row2, col2) = findPosition(matrix, cleanCipher[i + 1])

            when {
                row1 == row2 -> {
                    plainText.append(matrix[row1][(col1 + 4) % 5])
                    plainText.append(matrix[row2][(col2 + 4) % 5])
                }
                col1 == col2 -> {
                    plainText.append(matrix[(row1 + 4) % 5][col1])
                    plainText.append(matrix[(row2 + 4) % 5][col2])
                }
                else -> {
                    plainText.append(matrix[row1][col2])
                    plainText.append(matrix[row2][col1])
                }
            }
        }

        return plainText.toString().uppercase()
    }
}
