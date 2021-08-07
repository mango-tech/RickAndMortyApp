package com.mango.android.domain.usecase

import com.mango.android.domain.entity.CharacterEntity
import com.mango.android.domain.entity.CharacterQueryEntity
import com.mango.android.domain.interactor.OneOf
import com.mango.android.domain.repository.CharacterRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout

class GetSingleCharacterTest : TestCase() {
    fun `test list character should be the same as single character query`() = runBlocking {
        val character = CharacterEntity(1, "Rick", "Single", "Human?", "N/A", "M", "")
        val repository: CharacterRepository = mockk()
        coEvery { repository.getCharacters() } returns CharacterQueryEntity(0, 1, listOf(character))
        coEvery { repository.getCharacter(character.id) } returns character
        val useCase = GetSingleCharacter(repository)

        withTimeout(10000L) {
            useCase.exec(GetSingleCharacterParams(character.id)) {
                when (it) {
                    is OneOf.Error -> fail()
                    is OneOf.Success -> {
                        assert(it.data == character)
                    }
                }
            }
        }
    }
}