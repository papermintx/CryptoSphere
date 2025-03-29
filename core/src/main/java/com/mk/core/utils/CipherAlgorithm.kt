package com.mk.core.utils

enum class CipherAlgorithm(val algorithmName: String) {
    VIGENERE_CIPHER("Vigenere Cipher"),
    AUTO_KEY_VIGENERE("Auto-key Vigenere Cipher"),
    EXTENDED_VIGENERE("Extended Vigenere Cipher"),
    AFFINE_CIPHER("Affine Cipher"),
    PLAYFAIR_CIPHER("Playfair Cipher"),
    HILL_CIPHER("Hill Cipher");

    override fun toString(): String = algorithmName
}
