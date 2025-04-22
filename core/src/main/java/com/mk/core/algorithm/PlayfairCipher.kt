package com.mk.core.algorithm


object PlayfairCipher {

    private const val ALPHABET = "ABCDEFGHIKLMNOPQRSTUVWXYZ"  // 'J' digabungkan dengan 'I'

    // Fungsi untuk membersihkan teks
    private fun prepareText(text: String): String {
        // Hapus semua karakter non-alfabet dan ubah ke huruf besar
        return text.uppercase()
            .filter { it in ALPHABET }
    }

    // Fungsi untuk menyiapkan grid Playfair berdasarkan key
    private fun prepareKey(key: String): Array<CharArray> {
        val keyString = (key.uppercase()
            .filter { it in ALPHABET } + ALPHABET)
            .toSet().joinToString("")

        val grid = Array(5) { CharArray(5) }
        var index = 0

        for (row in 0 until 5) {
            for (col in 0 until 5) {
                grid[row][col] = keyString[index]
                index++
            }
        }
        return grid
    }

    // Fungsi untuk mencari posisi dalam grid
    private fun getPosition(c: Char, grid: Array<CharArray>): Pair<Int, Int> {
        for (row in 0 until 5) {
            for (col in 0 until 5) {
                if (grid[row][col] == c) {
                    return Pair(row, col)
                }
            }
        }
        return Pair(-1, -1) // Tidak ditemukan
    }

    // Fungsi untuk menambahkan pasangan huruf jika ada huruf yang sama
    private fun preparePairs(plainText: String): String {
        val text = StringBuilder()
        var i = 0
        while (i < plainText.length) {
            if (i + 1 < plainText.length && plainText[i] == plainText[i + 1]) {
                text.append(plainText[i]).append('X')
                i++
            } else if (i + 1 == plainText.length) {
                text.append(plainText[i]).append('X')
                i++
            } else {
                text.append(plainText[i]).append(plainText[i + 1])
                i += 2
            }
        }
        return text.toString()
    }

    // Fungsi untuk mengenkripsi teks
    fun encrypt(plainText: String, key: String): String {
        val cleanedText = prepareText(plainText)
        val preparedPairs = preparePairs(cleanedText)

        val grid = prepareKey(key)
        val cipherText = StringBuilder()

        for (i in 0 until preparedPairs.length step 2) {
            val char1 = preparedPairs[i]
            val char2 = preparedPairs[i + 1]

            val (row1, col1) = getPosition(char1, grid)
            val (row2, col2) = getPosition(char2, grid)

            if (row1 == row2) {
                cipherText.append(grid[row1][(col1 + 1) % 5])
                cipherText.append(grid[row2][(col2 + 1) % 5])
            } else if (col1 == col2) {
                cipherText.append(grid[(row1 + 1) % 5][col1])
                cipherText.append(grid[(row2 + 1) % 5][col2])
            } else {
                cipherText.append(grid[row1][col2])
                cipherText.append(grid[row2][col1])
            }
        }

        return cipherText.toString().chunked(5).joinToString(" ")
    }

    // Fungsi untuk mendekripsi teks
    fun decrypt(cipherText: String, key: String): String {
        val cleanedText = prepareText(cipherText)
        val grid = prepareKey(key)
        val plainText = StringBuilder()

        for (i in 0 until cleanedText.length step 2) {
            val char1 = cleanedText[i]
            val char2 = cleanedText[i + 1]

            val (row1, col1) = getPosition(char1, grid)
            val (row2, col2) = getPosition(char2, grid)

            if (row1 == row2) {
                plainText.append(grid[row1][(col1 + 4) % 5])
                plainText.append(grid[row2][(col2 + 4) % 5])
            } else if (col1 == col2) {
                plainText.append(grid[(row1 + 4) % 5][col1])
                plainText.append(grid[(row2 + 4) % 5][col2])
            } else {
                plainText.append(grid[row1][col2])
                plainText.append(grid[row2][col1])
            }
        }
        return plainText.toString()
    }
}