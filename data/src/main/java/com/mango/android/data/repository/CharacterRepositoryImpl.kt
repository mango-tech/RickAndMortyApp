package com.mango.android.data.repository

import com.mango.android.data.net.mapper.toCharacterQueryEntity
import com.mango.android.data.net.provider.NetProvider
import com.mango.android.domain.entity.CharacterQueryEntity
import com.mango.android.domain.repository.CharacterRepository

class CharacterRepositoryImpl(private val netProvider: NetProvider) : CharacterRepository {

    override suspend fun getCharacters(page: Int): CharacterQueryEntity {
        // Perfect place to call first to localProvider, if fails, then use netProvider
        return netProvider.getCharacters(page).toCharacterQueryEntity()
    }
}