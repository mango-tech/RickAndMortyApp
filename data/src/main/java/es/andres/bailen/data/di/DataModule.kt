package es.andres.bailen.data.di

import es.andres.bailen.data.repository.CharactersRepositoryImpl
import es.andres.bailen.domain.repository.CharactersRepository
import org.koin.core.module.Module
import org.koin.dsl.module

val networkModule: Module = module {

}


val repositoryModule: Module = module {
    single <CharactersRepository>{ CharactersRepositoryImpl() }
}

val dataModules = listOf(networkModule, repositoryModule)