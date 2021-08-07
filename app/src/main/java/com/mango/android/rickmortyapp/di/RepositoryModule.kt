package com.mango.android.rickmortyapp.di

import com.mango.android.data.net.provider.NetProvider
import com.mango.android.data.repository.CharacterRepositoryImpl
import com.mango.android.domain.repository.CharacterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideCharacterRepository(netProvider: NetProvider): CharacterRepository {
        return CharacterRepositoryImpl(netProvider)
    }

}