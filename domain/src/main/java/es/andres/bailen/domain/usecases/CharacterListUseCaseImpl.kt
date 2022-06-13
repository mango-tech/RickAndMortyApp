package es.andres.bailen.domain.usecases

import androidx.paging.PagingData
import es.andres.bailen.domain.models.CharacterModel
import es.andres.bailen.domain.models.DataResult
import es.andres.bailen.domain.repository.CharactersRepository
import kotlinx.coroutines.flow.Flow

class CharacterListUseCaseImpl(private val charactersRepository: CharactersRepository) :
    CharacterListUseCase {

    override fun getCharacterListPaged(): Flow<PagingData<CharacterModel>> {
        return charactersRepository.getCharactersPaged()
    }

    override suspend fun getCharacterDetail(characterId: String): DataResult<CharacterModel> {
        return charactersRepository.getCharacterDetail(characterId)
    }
}