package io.github.pps5.kakaosampleapp.di

import io.github.pps5.kakaosampleapp.data.repository.ConnpassRepository
import kotlinx.coroutines.CoroutineScope
import org.koin.dsl.module

val repositoryModule = module {
    single { (scope: CoroutineScope) -> ConnpassRepository(scope) }
}