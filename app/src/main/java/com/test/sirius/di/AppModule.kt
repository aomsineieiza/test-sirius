package com.test.sirius.di

import com.test.sirius.repository.SearchRepositoryImplement
import com.test.sirius.service.NetworkManager
import com.test.sirius.service.Service
import com.test.sirius.usecase.SearchCityUseCase
import com.test.sirius.viewmodel.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val repositoryModule = module {
    single {
        val network: NetworkManager = get()
        SearchRepositoryImplement(network.createService(Service::class.java))
    }
}

val serviceModule = module {
    single { NetworkManager() }
}

val useCaseModule = module {
    factory { SearchCityUseCase(get()) }
}

val viewModelModule = module {
    viewModel {
        SearchViewModel(
            get()
        )
    }
}
