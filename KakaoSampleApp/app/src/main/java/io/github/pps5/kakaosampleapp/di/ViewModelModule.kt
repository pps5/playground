package io.github.pps5.kakaosampleapp.di

import io.github.pps5.kakaosampleapp.presentation.viewmodel.NewArrivalViewModel
import io.github.pps5.kakaosampleapp.presentation.viewmodel.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { NewArrivalViewModel() }
    viewModel { SearchViewModel() }
}