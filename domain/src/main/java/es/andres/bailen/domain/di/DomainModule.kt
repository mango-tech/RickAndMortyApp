package es.andres.bailen.domain.di

import es.andres.bailen.domain.repository.CharactersRepository
import es.andres.bailen.domain.usecases.CharacterListUseCase
import es.andres.bailen.domain.usecases.CharacterListUseCaseImpl
import org.koin.core.module.Module
import org.koin.dsl.module

val interactionModule: Module = module {
    factory<CharacterListUseCase> { CharacterListUseCaseImpl(get()) }
}

val domainModules = listOf(interactionModule)