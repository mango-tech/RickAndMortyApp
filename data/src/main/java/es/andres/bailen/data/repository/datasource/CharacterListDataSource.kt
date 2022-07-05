package es.andres.bailen.data.repository.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import es.andres.bailen.data.extensions.getIntQueryParameter
import es.andres.bailen.data.mappers.mapCharacterDataToModel
import es.andres.bailen.data.network.api.RickyMortyApi
import es.andres.bailen.domain.models.CharacterModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CharacterListDataSource(private val api: RickyMortyApi): PagingSource<Int, CharacterModel>() {

    override fun getRefreshKey(state: PagingState<Int,  CharacterModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterModel> {
        return withContext(Dispatchers.IO) {
            val pagingModel  = api.getCharactersList(params.key ?: 0)
            val prevKey: Int? = pagingModel.info.prev?.getIntQueryParameter("page")
            val nextKey: Int? = pagingModel.info.next?.getIntQueryParameter("page")
            LoadResult.Page(
                pagingModel.results.map { mapCharacterDataToModel(it) },
                prevKey,
                nextKey
            )
        }
    }
}