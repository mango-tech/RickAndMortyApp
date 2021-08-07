package com.mango.android.data.repository

import com.mango.android.data.net.mapper.toCharacterEntity
import com.mango.android.data.net.mapper.toCharacterQueryEntity
import com.mango.android.data.net.provider.NetProvider
import com.mango.android.domain.entity.CharacterEntity
import com.mango.android.domain.entity.CharacterQueryEntity
import com.mango.android.domain.repository.CharacterRepository
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(private val netProvider: NetProvider) : CharacterRepository {

    override suspend fun getCharacters(page: Int): CharacterQueryEntity {
        // Perfect place to call first to localProvider, if fails, then use netProvider (repository strategy)
        return netProvider.getCharacters(page).toCharacterQueryEntity()
    }

    override suspend fun getCharacter(id: Int): CharacterEntity {
        return netProvider.getCharacter(id).toCharacterEntity()
    }
}