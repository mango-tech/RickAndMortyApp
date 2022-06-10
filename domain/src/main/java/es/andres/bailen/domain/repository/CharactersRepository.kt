package es.andres.bailen.domain.repository

import es.andres.bailen.domain.models.CharacterModel

interface CharactersRepository {
    fun getCharacters(): List<CharacterModel>

    fun getCharacterDetail(characterId: String): CharacterModel?
}