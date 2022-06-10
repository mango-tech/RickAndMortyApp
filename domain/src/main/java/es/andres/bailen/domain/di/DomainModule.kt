package es.andres.bailen.domain.di

import org.koin.core.module.Module
import org.koin.dsl.module

val interactionModule: Module = module {

}

val domainModules = listOf(interactionModule)