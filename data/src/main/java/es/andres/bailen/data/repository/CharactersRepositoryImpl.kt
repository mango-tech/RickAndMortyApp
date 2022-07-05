package es.andres.bailen.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import es.andres.bailen.data.mappers.mapCharacterDataToModel
import es.andres.bailen.data.network.api.RickyMortyApi
import es.andres.bailen.data.repository.datasource.CharacterListDataSource
import es.andres.bailen.domain.models.CharacterModel
import es.andres.bailen.domain.models.DataResult
import es.andres.bailen.domain.repository.CharactersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class CharactersRepositoryImpl(private val rickyMortyApi: RickyMortyApi) : CharactersRepository {

    private val characterPageConfig = PagingConfig(
        pageSize = 1,
        prefetchDistance = 5,
        enablePlaceholders = false,
        initialLoadSize = 3
    )

    override fun getCharactersPaged(): Flow<PagingData<CharacterModel>> {
        return Pager(characterPageConfig) {
            CharacterListDataSource(rickyMortyApi)
        }.flow.flowOn(Dispatchers.IO)
    }

    override suspend fun getCharacterDetail(characterId: String): DataResult<CharacterModel> {
        return try {
            DataResult.success(
                mapCharacterDataToModel(
                    rickyMortyApi.getCharacter(
                        characterId
                    )
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
            DataResult.error()
        }
    }
}