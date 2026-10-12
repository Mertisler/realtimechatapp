package com.loc.realtimechatapp.di

import android.app.Application
import androidx.room.Room
import com.loc.realtimechatapp.core.Constants
import com.loc.realtimechatapp.data.local.ChatDatabase
import com.loc.realtimechatapp.data.local.dao.MessageDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideChatDatabase(app: Application): ChatDatabase {
        return Room.databaseBuilder(
            app,
            ChatDatabase::class.java,
            Constants.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideMessageDao(db: ChatDatabase): MessageDao {
        return db.dao
    }
}