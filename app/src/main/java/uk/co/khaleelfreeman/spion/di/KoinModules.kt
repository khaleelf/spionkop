package uk.co.khaleelfreeman.spion.di

import org.koin.dsl.module
import uk.co.khaleelfreeman.service.ArticleNetworkService
import uk.co.khaleelfreeman.service.HttpClient
import uk.co.khaleelfreeman.service.retrofit.RetrofitClient
import uk.co.khaleelfreeman.spionkoparticledomain.repo.ArticleRepository
import uk.co.khaleelfreeman.spionkoparticledomain.repo.Repository
import uk.co.khaleelfreeman.spionkoparticledomain.service.NetworkService

val module = module {
    single<Repository> { ArticleRepository(get()) }
    single<NetworkService> { ArticleNetworkService(get()) }
    single<HttpClient> { RetrofitClient() }
}