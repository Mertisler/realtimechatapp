package com.loc.realtimechatapp.domain.usecase

import com.loc.realtimechatapp.domain.repository.ChatRepository
import javax.inject.Inject

class DisconnectSocketUseCase @Inject constructor(
    private val repository: ChatRepository
) {
    operator fun invoke() {
        repository.disconnect()
    }
}