package com.loc.realtimechatapp.di

import com.google.gson.Gson
import com.loc.realtimechatapp.data.remote.ChatWebSocketClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .pingInterval(30, TimeUnit.SECONDS) // Bağlantının kopmaması için sunucuya 30 saniyede bir ping atar
            .build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun provideChatWebSocketClient(
        client: OkHttpClient,
        gson: Gson
    ): ChatWebSocketClient {
        // Hilt, yukarıdaki OkHttpClient ve Gson fonksiyonlarını çalıştırıp ürettiği
        // nesneleri buraya parametre olarak otomatik getirir.
        return ChatWebSocketClient(client, gson)
    }
}