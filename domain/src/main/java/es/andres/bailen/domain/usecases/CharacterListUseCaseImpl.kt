package es.andres.bailen.domain.usecases

import es.andres.bailen.domain.models.CharacterModel
import es.andres.bailen.domain.models.DataResult
import es.andres.bailen.domain.repository.CharactersRepository

class CharacterListUseCaseImpl(private val charactersRepository: CharactersRepository) :
    CharacterListUseCase {

    override fun getCharacterList(): DataResult<List<CharacterModel>> {
        return charactersRepository.getCharacters()
    }

    override fun getCharacterDetail(characterId: String): CharacterModel? {
        return charactersRepository.getCharacterDetail(characterId)
    }
}