package com.mk.core.domain.usecase

import com.mk.core.data.room.model.EncryptEntity
import com.mk.core.domain.repository.CryptoSphereRepository

class InsertHistoryUseCase(
    private val repository: CryptoSphereRepository
) {

    suspend operator fun invoke(
      entity: EncryptEntity
    ) {
        repository.insertHistory(entity)
    }

}