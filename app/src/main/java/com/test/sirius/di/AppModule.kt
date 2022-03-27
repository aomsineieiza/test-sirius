package com.test.sirius.di

import com.test.sirius.repository.MainRepositoryImplement
import com.test.sirius.service.NetworkManager
import com.test.sirius.service.Service
import com.test.sirius.usecase.GetCitiesListUseCase
import com.test.sirius.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val repositoryModule = module {
    single {
        val network: NetworkManager = get()
        MainRepositoryImplement(network.createService(Service::class.java))
    }
}

val serviceModule = module {
    single { NetworkManager() }
}

val useCaseModule = module {
    factory { GetCitiesListUseCase(get()) }
}

val viewModelModule = module {
    viewModel {
        MainViewModel(
            get()
        )
    }
}
