package uk.co.khaleelfreeman.spion.di

import org.koin.dsl.module
import uk.co.khaleelfreeman.spion.repo.ArticleRepository
import uk.co.khaleelfreeman.spion.repo.Repository
import uk.co.khaleelfreeman.spion.service.ArticleNetworkService
import uk.co.khaleelfreeman.spion.service.HttpClient
import uk.co.khaleelfreeman.spion.service.NetworkService
import uk.co.khaleelfreeman.spion.service.retrofit.RetrofitClient

val module = module {
    single<Repository> { ArticleRepository(get()) }
    single<NetworkService>{ ArticleNetworkService(get()) }
    single<HttpClient>{ RetrofitClient() }
}