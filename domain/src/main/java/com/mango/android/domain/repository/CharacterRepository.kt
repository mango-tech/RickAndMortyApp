package com.mango.android.domain.repository

import com.mango.android.domain.entity.CharacterEntity
import com.mango.android.domain.entity.CharacterQueryEntity
import com.mango.android.domain.interactor.Failure
import com.mango.android.domain.interactor.OneOf

interface CharacterRepository {

    suspend fun getCharacters(page: Int = 1): OneOf<Failure, CharacterQueryEntity>

    suspend fun getCharacter(id: Int): OneOf<Failure, CharacterEntity>
}