package com.mango.android.data.net.provider

import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

class NetProviderImplTest : TestCase() {

    private lateinit var provider: NetProvider

    @Before
    public override fun setUp() {
        super.setUp()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(JacksonConverterFactory.create())
            .build().create(RetrofitAPI::class.java)
        provider = NetProviderImpl(retrofit)
    }

    @Test
    fun testGetCharacters() = runBlocking {
        val chars = provider.getCharacters(1)
        assert(chars.results.distinct().size == 20)

        try {
            provider.getCharacters(chars.info.pages + 1)
            fail()
        } catch (e: Exception) {
            assert(true)
        }

    }
}