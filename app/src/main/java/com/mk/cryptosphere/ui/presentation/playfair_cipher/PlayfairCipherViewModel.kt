package com.mk.cryptosphere.ui.presentation.playfair_cipher

import androidx.lifecycle.ViewModel
import com.mk.core.domain.usecase.DecryptPlayfairCipherUseCase
import com.mk.core.domain.usecase.EncryptPlayfairCipherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlayfairCipherViewModel @Inject constructor(
    private val encryptPlayfairCipherUseCase: EncryptPlayfairCipherUseCase,
    private val decryptPlayfairCipherUseCase: DecryptPlayfairCipherUseCase
) : ViewModel(){


}