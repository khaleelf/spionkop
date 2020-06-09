package uk.co.khaleelfreeman.spion.di

import okhttp3.OkHttpClient
import okhttp3.mock.*
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import uk.co.khaleelfreeman.service.ArticleNetworkService
import uk.co.khaleelfreeman.service.HttpClient
import uk.co.khaleelfreeman.service.retrofit.Articles
import uk.co.khaleelfreeman.service.retrofit.RetrofitFactory
import uk.co.khaleelfreeman.spion.ui.MainActivityViewModel
import uk.co.khaleelfreeman.spionkoparticledomain.repo.ArticleRepository
import uk.co.khaleelfreeman.spionkoparticledomain.repo.Repository
import uk.co.khaleelfreeman.spionkoparticledomain.service.NetworkService

val module = module {
    viewModel { MainActivityViewModel(get()) }
    single<Repository> { ArticleRepository(get()) }
    single<NetworkService> { ArticleNetworkService(get()) }
    single<HttpClient> { TestHttpClient() }
}

/**
 * The original service has been deprecated so this intercepts network requests and
 * returns local JSON - originally used for the tests.
 */
class TestHttpClient : HttpClient {
    private val interceptor = MockInterceptor().apply {
        rule(get, url eq "https://www.khaleelfreeman.co.uk/liverpoolfc/articles") {
            respond(ClasspathResources.resource("ExampleResponse.json"), MediaTypes.MEDIATYPE_JSON)
        }
    }
    private val client = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()
    private val retrofit = RetrofitFactory.builder().client(client).build()

    override val service: Articles = retrofit.create(Articles::class.java)
}