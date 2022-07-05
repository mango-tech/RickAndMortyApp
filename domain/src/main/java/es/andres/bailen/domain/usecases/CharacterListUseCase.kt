package es.andres.bailen.domain.usecases

import androidx.paging.PagingData
import es.andres.bailen.domain.models.CharacterModel
import es.andres.bailen.domain.models.DataResult
import kotlinx.coroutines.flow.Flow

interface CharacterListUseCase {

    fun getCharacterListPaged(): Flow<PagingData<CharacterModel>>

    suspend fun getCharacterDetail(characterId: String): DataResult<CharacterModel>
}