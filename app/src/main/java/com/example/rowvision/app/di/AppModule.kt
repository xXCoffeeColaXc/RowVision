package com.example.rowvision.app.di

import com.example.rowvision.app.data.DefaultSessionRepository
import com.example.rowvision.app.data.SessionRepository
import com.example.rowvision.app.data.DefaultUserRepository
import com.example.rowvision.app.data.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module binding data repositories.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    @Singleton
    abstract fun bindUserRepository(
        defaultUserRepository: DefaultUserRepository
    ): UserRepository

    @Binds
    @Singleton
    abstract fun bindSessionRepository(
        defaultSessionRepository: DefaultSessionRepository
    ): SessionRepository
}