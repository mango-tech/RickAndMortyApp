package es.andres.bailen.domain.usecases

import es.andres.bailen.domain.models.CharacterModel
import es.andres.bailen.domain.models.DataResult

interface CharacterListUseCase {

    suspend fun getCharacterList(): DataResult<List<CharacterModel>>

    suspend fun getCharacterDetail(characterId: String): DataResult<CharacterModel>
}