package com.mango.android.data.repository

import com.mango.android.data.DefaultTestCase
import com.mango.android.data.net.provider.NetProviderImpl
import com.mango.android.data.net.provider.RetrofitAPI
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
    fun testObtainFirstPage() = runBlocking {
        val firstQuery = repository.getCharacters()

        assert(firstQuery.data.isNotEmpty())

        val lastQuery = repository.getCharacters(firstQuery.pages)

        assert(lastQuery.data.isNotEmpty())

        try {
            repository.getCharacters(lastQuery.pages + 1)
            fail()
        } catch (e: Exception) {
            assert(true)
        }
    }
}