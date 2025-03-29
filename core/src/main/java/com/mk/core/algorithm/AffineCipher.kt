package com.mk.core.algorithm

object AffineCipher {
    private const val ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

    // Encrypt function: C = (aP + b) mod 26
    fun encrypt(plaintext: String, a: Int, b: Int): String {
        require(gcd(a, 26) == 1) { "Key 'a' must be coprime with 26" }

        return plaintext.uppercase().map { char ->
            if (char in ALPHABET) {
                val p = ALPHABET.indexOf(char)
                val c = (a * p + b) % 26
                ALPHABET[c]
            } else char
        }.joinToString("")
    }

    fun decrypt(ciphertext: String, a: Int, b: Int): String {
        require(gcd(a, 26) == 1) { "Key 'a' must be coprime with 26" }
        val aInv = modInverse(a, 26)

        return ciphertext.uppercase().map { char ->
            if (char in ALPHABET) {
                val c = ALPHABET.indexOf(char)
                val p = (aInv * (c - b + 26)) % 26
                ALPHABET[p]
            } else char
        }.joinToString("")
    }

    private fun modInverse(a: Int, m: Int): Int {
        for (x in 1 until m) {
            if ((a * x) % m == 1) return x
        }
        throw IllegalArgumentException("No modular inverse found for a = $a and m = $m")
    }

    private fun gcd(a: Int, b: Int): Int {
        return if (b == 0) a else gcd(b, a % b)
    }
}