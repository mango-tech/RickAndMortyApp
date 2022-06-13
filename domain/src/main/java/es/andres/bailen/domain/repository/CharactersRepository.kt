package es.andres.bailen.domain.repository

import androidx.paging.PagingData
import es.andres.bailen.domain.models.CharacterModel
import es.andres.bailen.domain.models.DataResult
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {
    suspend fun getCharacters(): DataResult<List<CharacterModel>>

    fun getCharactersPaged(): Flow<PagingData<CharacterModel>>

    suspend fun getCharacterDetail(characterId: String): DataResult<CharacterModel>
}