package com.mango.android.data.net.mapper

import com.mango.android.data.net.model.CharacterResult
import junit.framework.TestCase
import org.junit.Test

class CharacterResultMapperTest : TestCase() {

    public override fun setUp() {
        super.setUp()
    }

    @Test
    fun testEqualData() {
        val model = CharacterResult(1, "Test", "Status", "N/A", "TYPE", "M/F/D", "NONE")
        val entity = model.toCharacterEntity()

        assertEquals(model.id, entity.id)
        assertEquals(model.name, entity.name)
        assertEquals(model.status, entity.status)
        assertEquals(model.species, entity.species)
        assertEquals(model.type, entity.type)
        assertEquals(model.gender, entity.gender)
        assertEquals(model.image, entity.image)
    }
}