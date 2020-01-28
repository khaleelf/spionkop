package uk.co.khaleelfreeman.spion.di

import org.koin.dsl.module
import uk.co.khaleelfreeman.spion.repo.ArticleRepository
import uk.co.khaleelfreeman.spion.repo.Repository
import co.uk.khaleelfreeman.service.ArticleNetworkService
import co.uk.khaleelfreeman.service.HttpClient
import co.uk.khaleelfreeman.service.NetworkService
import co.uk.khaleelfreeman.service.retrofit.RetrofitClient

val module = module {
    single<Repository> { ArticleRepository(get()) }
    single<NetworkService>{ ArticleNetworkService(get()) }
    single<HttpClient>{ RetrofitClient() }
}