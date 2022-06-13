package es.andres.bailen.domain.usecases

import es.andres.bailen.domain.models.CharacterModel
import es.andres.bailen.domain.models.DataResult

interface CharacterListUseCase {

    fun getCharacterList(): DataResult<List<CharacterModel>>

    fun getCharacterDetail(characterId: String): CharacterModel?
}