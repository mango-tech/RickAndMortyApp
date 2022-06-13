package es.andres.bailen.data

import es.andres.bailen.data.network.api.RickyMortyApi
import es.andres.bailen.data.network.model.CharacterDataModel
import es.andres.bailen.data.repository.CharactersRepositoryImpl
import es.andres.bailen.domain.models.CharacterModel
import es.andres.bailen.domain.models.DataResult
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class CharacterListRepository {
    private val api = mock(RickyMortyApi::class.java)

    @Test
    fun testGetCharacterNullResult() = runTest {
        val repository = CharactersRepositoryImpl(api)
        `when`(api.getCharacter("0")).thenReturn(null)
        //`when`(repository.getCharacterDetail("0")).thenReturn(DataResult.success(createFakeCharacterModel()))
        val response = repository.getCharacterDetail("0")
        Assert.assertEquals(response.status, DataResult.Status.ERROR)
    }

    @Test
    fun testGetCharacterResult() = runTest {
        val repository = CharactersRepositoryImpl(api)
        `when`(api.getCharacter("0")).thenReturn(createFakeCharacterData())
        //`when`(repository.getCharacterDetail("0")).thenReturn(DataResult.success(createFakeCharacterModel()))
        val response = repository.getCharacterDetail("0")
        Assert.assertEquals(response.status, DataResult.Status.SUCCESS)
    }


    private fun createFakeCharacterData(): CharacterDataModel = CharacterDataModel(
        1,
        "Fake",
        CharacterDataModel.Status.ALIVE,
        "specie",
        "type",
        CharacterDataModel.Gender.GENDERLESS,
        "image"
    )
}