package com.mango.android.rickmortyapp.di

import com.mango.android.rickmortyapp.ui.viewmodel.details.DetailViewModel
import com.mango.android.rickmortyapp.ui.viewmodel.list.ListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val presentationModule: Module = module {
    viewModel { ListViewModel(get()) }
    viewModel { DetailViewModel(get()) }

}

val appModules = listOf(presentationModule)