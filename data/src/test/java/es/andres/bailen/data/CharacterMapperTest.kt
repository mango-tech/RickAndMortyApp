package es.andres.bailen.data

import es.andres.bailen.data.mappers.mapCharacterDataToModel
import es.andres.bailen.data.network.model.CharacterDataModel
import es.andres.bailen.domain.models.CharacterModel
import org.junit.Test
import org.junit.runner.RunWith

class CharacterMapperTest {

    @Test
    fun testCharacterMapper() {
        val character = createFakeCharacterData()
        val mappedModel = mapCharacterDataToModel(character)
        assert(mappedModel is CharacterModel)
        assert(character.id == mappedModel.id)
        assert(character.name == mappedModel.name)
        assert(character.image == mappedModel.image)
        assert(character.gender.toString() == mappedModel.gender)
        assert(character.status.toString() == mappedModel.status.toString())
    }

    private fun createFakeCharacterData(): CharacterDataModel = CharacterDataModel(
        1,
        "Fake",
        CharacterDataModel.Status.ALIVE,
        "specie",
        "type",
        CharacterDataModel.Gender.GENDERLESS,
        "image"
    )
}