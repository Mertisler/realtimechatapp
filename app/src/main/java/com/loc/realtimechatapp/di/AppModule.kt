package com.loc.realtimechatapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // Tüm uygulama boyunca tek bir örneği (Singleton) yaşayacak
object AppModule {

    // RepositoryImpl'in beklediği "currentUserName" bilgisini sağlıyoruz.
    // Gerçek bir senaryoda bu değer DataStore veya SharedPreferences'tan okunur.
    @Provides
    @Singleton
    @Named("currentUserName")
    fun provideCurrentUserName(): String {
        return "Mert" // Geçici olarak sabit bir kullanıcı adı
    }
}