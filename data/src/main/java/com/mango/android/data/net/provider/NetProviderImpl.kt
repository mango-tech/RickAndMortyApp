package com.mango.android.data.net.provider

import com.mango.android.data.net.model.CharacterResponse

class NetProviderImpl(private val retrofitAPI: RetrofitAPI) : NetProvider {

    override suspend fun getCharacters(page: Int): CharacterResponse {
        return retrofitAPI.getCharacters(page)
    }
}