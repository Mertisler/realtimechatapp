package com.loc.realtimechatapp.domain.usecase

import com.loc.realtimechatapp.domain.model.Message
import com.loc.realtimechatapp.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMessagesUseCase @Inject constructor(
    private val repository: ChatRepository
) {
    // operator fun invoke() sayesinde bu sınıf ViewModel içinde getMessages() şeklinde direkt çağrılabilir.
    operator fun invoke(): Flow<List<Message>> {
        return repository.observeMessages()
    }
}