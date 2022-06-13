package es.andres.bailen.domain.repository

import es.andres.bailen.domain.models.CharacterModel
import es.andres.bailen.domain.models.DataResult

interface CharactersRepository {
    fun getCharacters(): DataResult<List<CharacterModel>>

    suspend fun getCharacterDetail(characterId: String): DataResult<CharacterModel>
}