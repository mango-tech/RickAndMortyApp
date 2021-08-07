package com.mango.android.data.net.provider

import com.mango.android.data.net.model.CharacterResponse
import com.mango.android.data.net.model.CharacterResult

interface NetProvider {

    suspend fun getCharacters(page: Int): CharacterResponse

    suspend fun getCharacter(id: Int): CharacterResult
}