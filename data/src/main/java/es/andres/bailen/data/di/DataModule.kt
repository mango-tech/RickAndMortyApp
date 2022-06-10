package es.andres.bailen.data.di

import org.koin.core.module.Module
import org.koin.dsl.module

val networkModule: Module = module {

}


val repositoryModule: Module = module {

}

val dataModules = listOf(networkModule, repositoryModule)