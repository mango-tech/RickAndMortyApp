package com.mango.android.domain.usecase

import com.mango.android.domain.entity.CharacterQueryEntity
import com.mango.android.domain.interactor.Failure
import com.mango.android.domain.interactor.OneOf
import com.mango.android.domain.repository.CharacterRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout

class GetCharactersTest : TestCase() {

    public override fun setUp() {
        super.setUp()
    }

    @ExperimentalCoroutinesApi
    fun `test if repository returns value to use case`() = runBlocking {
        val defaultQuery = CharacterQueryEntity(0, 1, mutableListOf())
        val repository: CharacterRepository = mockk()
        coEvery { repository.getCharacters() } returns OneOf.Success(defaultQuery)

        repository.getCharacters(1)

        val useCase = GetCharacters(repository)
        withTimeout(10000L) {
            useCase.exec(GetCharactersParams(1)) {
                when (it) {
                    is OneOf.Error -> fail()
                    is OneOf.Success -> {
                        assert(it.data.pages == 1)
                    }
                }
            }
        }
    }

    fun `test repository error`() = runBlocking {
        val repository: CharacterRepository = mockk()
        coEvery { repository.getCharacters() } returns OneOf.Error(Failure.NetworkFailure)
        val useCase = GetCharacters(repository)
        useCase.exec(GetCharactersParams(1)) {
            when (it) {
                is OneOf.Error -> {

                }
                is OneOf.Success -> fail()
            }
        }
    }

}