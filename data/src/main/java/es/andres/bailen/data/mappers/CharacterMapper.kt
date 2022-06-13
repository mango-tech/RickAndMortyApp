package es.andres.bailen.data.mappers

import es.andres.bailen.data.model.CharacterDataModel
import es.andres.bailen.domain.models.CharacterModel

class CharacterMapper {
    fun mapCharacterDataToModel(character: CharacterDataModel): CharacterModel {
        return CharacterModel(
            id = character.id,
            name = character.name,
            status = character.status.toString(),
            species = character.species,
            type = character.type,
            gender = character.gender.toString(),
            image = character.image,

        )
    }

}