package uk.co.khaleelfreeman.spion.di

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import uk.co.khaleelfreeman.service.ArticleNetworkService
import uk.co.khaleelfreeman.service.HttpClient
import uk.co.khaleelfreeman.service.retrofit.RetrofitClient
import uk.co.khaleelfreeman.spion.ui.MainActivityViewModel
import uk.co.khaleelfreeman.spionkoparticledomain.repo.ArticleRepository
import uk.co.khaleelfreeman.spionkoparticledomain.repo.Repository
import uk.co.khaleelfreeman.spionkoparticledomain.service.NetworkService

val module = module {
    viewModel{ MainActivityViewModel(get()) }
    single<Repository> { ArticleRepository(get()) }
    single<NetworkService> { ArticleNetworkService(get()) }
    single<HttpClient> { RetrofitClient() }
}