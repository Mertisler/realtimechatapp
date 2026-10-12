package com.loc.realtimechatapp.di

import com.loc.realtimechatapp.data.local.dao.MessageDao
import com.loc.realtimechatapp.data.remote.ChatWebSocketClient
import com.loc.realtimechatapp.data.repository.ChatRepositoryImpl
import com.loc.realtimechatapp.domain.repository.ChatRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideChatRepository(
        messageDao: MessageDao,
        webSocketClient: ChatWebSocketClient,
        @Named("currentUserName") currentUserName: String
    ): ChatRepository {
        // ViewModel, ChatRepository istediğinde Hilt arka planda bu ChatRepositoryImpl nesnesini verecektir.
        return ChatRepositoryImpl(messageDao, webSocketClient, currentUserName)
    }
}