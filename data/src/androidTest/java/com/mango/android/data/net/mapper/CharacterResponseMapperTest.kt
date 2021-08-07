package com.mango.android.data.net.mapper

import com.mango.android.data.net.model.CharacterRequestInfo
import com.mango.android.data.net.model.CharacterResponse
import com.mango.android.data.net.model.CharacterResult
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking

class CharacterResponseMapperTest : TestCase() {

    fun testResponseEqual() = runBlocking {
        val modelInfo = CharacterRequestInfo(5, 1, "", "")
        val modelCharacters = mutableListOf<CharacterResult>()
        val model = CharacterResponse(modelInfo, modelCharacters)
        val entity = model.toCharacterQueryEntity()

        assertEquals(model.info.count, entity.count)
        assertEquals(model.info.pages, entity.pages)
        assertEquals(model.results.size, entity.data.size)
    }
}