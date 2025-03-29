package com.mk.core.domain.usecase

import com.mk.core.domain.repository.CryptoSphereRepository

class DecryptPlayfairCipherUseCase (
    private val cryptoSphereRepository: CryptoSphereRepository
){
    operator fun invoke(ciphertext: String, key: String) =
        cryptoSphereRepository.decryptPlayfairCipher(ciphertext, key)
}