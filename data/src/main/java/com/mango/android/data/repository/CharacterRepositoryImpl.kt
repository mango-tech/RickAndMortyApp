package com.mango.android.data.repository

import com.mango.android.data.net.mapper.toCharacterEntity
import com.mango.android.data.net.mapper.toCharacterQueryEntity
import com.mango.android.data.net.provider.NetProvider
import com.mango.android.domain.entity.CharacterEntity
import com.mango.android.domain.entity.CharacterQueryEntity
import com.mango.android.domain.interactor.Failure
import com.mango.android.domain.interactor.OneOf
import com.mango.android.domain.repository.CharacterRepository
import java.io.IOException
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(private val netProvider: NetProvider) :
    CharacterRepository {

    override suspend fun getCharacters(page: Int): OneOf<Failure, CharacterQueryEntity> {
        // Perfect place to call first to localProvider, if fails, then use netProvider (repository strategy)
        if (page <= 0)
            return OneOf.Error(Failure.OutOfBoundsFailure)

        return try {
            OneOf.Success(netProvider.getCharacters(page).toCharacterQueryEntity())
        } catch (e: IOException) {
            OneOf.Error(Failure.NetworkFailure)
        } catch (e: Exception) {
            OneOf.Error(Failure.UnknownFailure)
        }
    }

    override suspend fun getCharacter(id: Int): OneOf<Failure, CharacterEntity> {
        return try {
            OneOf.Success(netProvider.getCharacter(id).toCharacterEntity())
        } catch (e: IOException) {
            OneOf.Error(Failure.NetworkFailure)
        } catch (e: Exception) {
            OneOf.Error(Failure.UnknownFailure)
        }
    }
}