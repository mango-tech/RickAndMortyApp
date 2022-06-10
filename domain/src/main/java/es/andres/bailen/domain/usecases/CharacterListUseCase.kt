package es.andres.bailen.domain.usecases

import es.andres.bailen.domain.models.CharacterModel

interface CharacterListUseCase {

    fun getCharacterList(): List<CharacterModel>

    fun getCharacterDetail(characterId: String): CharacterModel?
}