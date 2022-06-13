package es.andres.bailen.data.repository.di

import es.andres.bailen.data.network.di.networkModule
import es.andres.bailen.data.repository.CharactersRepositoryImpl
import es.andres.bailen.domain.repository.CharactersRepository
import org.koin.core.module.Module
import org.koin.dsl.module


val repositoryModule: Module = module {
    factory  <CharactersRepository>{ CharactersRepositoryImpl(get()) }
}

val dataModules = listOf(networkModule, repositoryModule)