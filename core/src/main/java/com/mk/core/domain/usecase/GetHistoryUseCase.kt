package com.mk.core.domain.usecase

import com.mk.core.data.room.model.EncryptEntity
import com.mk.core.domain.repository.CryptoSphereRepository
import kotlinx.coroutines.flow.Flow

class GetHistoryUseCase(
    private val cryptoSphereRepository: CryptoSphereRepository
) {

    operator fun invoke(cipherName: String, sortedDesc: Boolean = true): Flow<List<EncryptEntity>> {
        return cryptoSphereRepository.getHistoryByCipher(cipherName, sortedDesc)
    }
}