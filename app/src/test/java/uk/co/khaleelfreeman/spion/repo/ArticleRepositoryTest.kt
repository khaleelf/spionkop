package uk.co.khaleelfreeman.spion.repo

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import uk.co.khaleelfreeman.spion.service.Article
import uk.co.khaleelfreeman.spion.service.ArticleResponse
import uk.co.khaleelfreeman.spion.service.NetworkService
import uk.co.khaleelfreeman.spion.service.Visual
import java.lang.reflect.Array

class ArticleRepositoryTest {

    private val testNetworkService = TestNetworkService()
    private val articleRepository = ArticleRepository(testNetworkService)

    @Before
    fun setup() {
        articleRepository.fetchArticles {  }
    }

    @Test
    fun `getSources() strips out the domain name from the url and returns a unique set`() {
        assert(articleRepository.getSources().containsAll((1..10).toSet().map { it.toString() }))
    }

    @Test
    fun `getPublished() returns the published time from the service`() {
        testNetworkService.execute {
            assertEquals(articleRepository.published, it.published)
        }
    }

    @Test
    fun `getArticles() should return the articles from the service`() {
        assertArrayEquals(articleRepository.getArticles(), testNetworkService.articles)
    }

    @Test
    fun `addFilter() should all remove articles that don't match filter`(){
        articleRepository.addFilter("10")
        assert(articleRepository.getArticles().contentDeepEquals(arrayOf(Article(title="10", visual=Visual(url="10"), timeStamp="10", url="https:://www.10.com/liverpool"))))
    }

    @Test
    fun `removeFilter() should add articles when no filter applied`(){
        articleRepository.removeFilter("10")
        assert(articleRepository.getArticles().contentDeepEquals(testNetworkService.articles))
    }
}

class TestNetworkService : NetworkService {

    val articles = generateTestArticles()

    override fun execute(response: (ArticleResponse) -> Unit) {
        response(ArticleResponse(1578574582000, articles))
    }

    private fun generateTestArticles() : kotlin.Array<Article> {
        return (1..10).map { it.toString() }.fold(emptyArray(),{acc, item ->
            acc.plus(Article(item, Visual(item),item,url = "https:://www.${item}.com/liverpool"))
        })
    }

}