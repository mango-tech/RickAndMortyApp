package es.andres.bailen.domain.repository

import es.andres.bailen.domain.models.CharacterModel
import es.andres.bailen.domain.models.DataResult

interface CharactersRepository {
    fun getCharacters(): DataResult<List<CharacterModel>>

    fun getCharacterDetail(characterId: String): CharacterModel?
}