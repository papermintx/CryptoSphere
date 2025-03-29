package com.mk.core.domain.usecase

import android.net.Uri
import com.mk.core.domain.repository.CryptoSphereRepository

class DecryptExtendedVigereCipherUseCase(
    private val cryptoSphereRepository: CryptoSphereRepository
){
    operator fun invoke(uri: Uri, key: String) = cryptoSphereRepository.decryptFile(uri, key)
}