package com.mango.android.rickmortyapp.di

import com.mango.android.data.net.provider.NetProvider
import com.mango.android.data.net.provider.NetProviderImpl
import com.mango.android.data.net.provider.RetrofitAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ProviderModule {

    @Provides
    @Singleton
    fun provideRetrofitAPI(): RetrofitAPI {
        return RetrofitAPI.create("https://rickandmortyapi.com/api/")
    }

    @Provides
    @Singleton
    fun provideNetProvider(retrofitAPI: RetrofitAPI): NetProvider {
        return NetProviderImpl(retrofitAPI)
    }

}