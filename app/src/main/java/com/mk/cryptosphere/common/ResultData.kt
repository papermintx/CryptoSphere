package com.mk.cryptosphere.common

data class ResultData(
    val ciphertext: String = "",
    val key: String = "",
    val keyA : String = "",
    val keyB : String = "",
    val plaintext: String = "",
    val isDecrypt: Boolean = false
)