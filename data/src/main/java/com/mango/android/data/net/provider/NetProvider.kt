package com.mango.android.data.net.provider

import com.mango.android.data.net.model.CharacterResponse

interface NetProvider {

    suspend fun getCharacters(page: Int): CharacterResponse

}