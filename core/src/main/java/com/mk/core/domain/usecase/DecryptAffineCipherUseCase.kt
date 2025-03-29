package com.mk.core.domain.usecase

import com.mk.core.domain.repository.CryptoSphereRepository

class DecryptAffineCipherUseCase(
    private val repository: CryptoSphereRepository
) {
    operator fun invoke(ciphertext: String, key: Pair<Int, Int>) = repository.decryptAffineCipher(ciphertext, key)
}
