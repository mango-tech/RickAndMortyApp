package com.mango.android.data.repository

import com.mango.android.data.DefaultTestCase
import com.mango.android.data.net.provider.NetProviderImpl
import com.mango.android.data.net.provider.RetrofitAPI
import com.mango.android.domain.interactor.OneOf
import com.mango.android.domain.repository.CharacterRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory


class CharacterRepositoryImplTest : DefaultTestCase() {

    private lateinit var repository: CharacterRepository

    public override fun setUp() {
        super.setUp()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(JacksonConverterFactory.create())
            .build().create(RetrofitAPI::class.java)
        val netProvider = NetProviderImpl(retrofit)

        repository = CharacterRepositoryImpl(netProvider)
    }

    @Test
    fun testOutOfBounds() = runBlocking {
        when(repository.getCharacters(0)){
            is OneOf.Success -> fail()
            is OneOf.Error -> {}
        }
    }

    @Test
    fun testObtainFirstAndLastPage() = runBlocking {
        val firstQuery = repository.getCharacters()
        when (firstQuery) {
            is OneOf.Error -> fail()
            is OneOf.Success -> assert(firstQuery.data.data.isNotEmpty())
        }

        val lastQuery = repository.getCharacters((firstQuery as OneOf.Success).data.pages)

        when (lastQuery) {
            is OneOf.Error -> fail()
            is OneOf.Success -> assert(lastQuery.data.data.isNotEmpty())
        }

        val page = (lastQuery as OneOf.Success).data.pages

        when (repository.getCharacters(page + 1)) {
            is OneOf.Error -> {}
            is OneOf.Success -> fail()
        }

    }
}