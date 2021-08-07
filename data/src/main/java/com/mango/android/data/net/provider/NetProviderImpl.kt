package com.mango.android.data.net.provider

import com.mango.android.data.net.model.CharacterResponse
import com.mango.android.data.net.model.CharacterResult
import javax.inject.Inject

class NetProviderImpl @Inject constructor(private val retrofitAPI: RetrofitAPI) : NetProvider {

    override suspend fun getCharacters(page: Int): CharacterResponse {
        return retrofitAPI.getCharacters(page)
    }

    override suspend fun getCharacter(id: Int): CharacterResult {
        return retrofitAPI.getCharacter(id)
    }
}