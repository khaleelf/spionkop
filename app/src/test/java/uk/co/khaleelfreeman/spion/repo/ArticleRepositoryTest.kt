package uk.co.khaleelfreeman.spion.repo

import io.reactivex.Single
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import uk.co.khaleelfreeman.service.NetworkService
import uk.co.khaleelfreeman.service.retrofit.dto.Article
import uk.co.khaleelfreeman.service.retrofit.dto.ArticleResponse
import uk.co.khaleelfreeman.service.retrofit.dto.Visual

class ArticleRepositoryTest {

    private val testNetworkService = TestNetworkService()
    private val articleRepository = ArticleRepository(testNetworkService)

    @Before
    fun setup() {
        articleRepository.fetchArticles { }
    }

    @Test
    fun `getSources() strips out the domain name from the url and returns a unique set`() {
        val expectedSources = (1..10).toSet()
            .map { "$it $it" } // a source will look like "1 1" according to how the TestNetworkService is setup.
        assert(articleRepository.getSources().containsAll(expectedSources))
    }

    @Test
    fun `getPublished() returns the published time from the service`() {
        val expected = testNetworkService.published
        assertEquals(expected, articleRepository.published)
    }

    @Test
    fun `getArticles() should return the articles from the service`() {
        val expected = testNetworkService.articles
        assertArrayEquals(expected, articleRepository.getArticles())
    }

    @Test
    fun `addFilter() should contain only articles that match the filter`() {
        articleRepository.addFilter("10 10")
        val expected = arrayOf(
            Article(
                title = "10",
                visual = Visual(url = "10"),
                timeStamp = 10,
                url = "https:://www.10 10.com/liverpool"
            )
        )
        assert(articleRepository.getArticles().contentDeepEquals(expected))
    }

    @Test
    fun `addFilter() called with different filters should return article array with multiple articles`() {
        articleRepository.addFilter("10 10")
        articleRepository.addFilter("9 9")
        val expected = arrayOf(
            Article(
                title = "10",
                visual = Visual(url = "10"),
                timeStamp = 10,
                url = "https:://www.10 10.com/liverpool"
            ),
            Article(
                title = "9",
                visual = Visual(url = "9"),
                timeStamp = 9,
                url = "https:://www.9 9.com/liverpool"
            )
        )
        assert(articleRepository.getArticles().contentDeepEquals(expected))
    }

    @Test
    fun `removeFilter() should remove articles from the filterd array`() {
        articleRepository.addFilter("10 10")
        articleRepository.addFilter("9 9")
        articleRepository.removeFilter("10 10")
        val expected = arrayOf(
            Article(
                title = "9",
                visual = Visual(url = "9"),
                timeStamp = 9,
                url = "https:://www.9 9.com/liverpool"
            )
        )
        assert(articleRepository.getArticles().contentDeepEquals(expected))
    }
}

class TestNetworkService : NetworkService {

    val articles = generateTestArticles()
    val published = 1578574582000

    override fun execute(): Single<ArticleResponse> {
        return Single.create<ArticleResponse> {
            it.onSuccess(ArticleResponse(published, articles))
        }
    }

    private fun generateTestArticles(): kotlin.Array<Article> {
        return (1..10).map { it.toString() }.fold(emptyArray(), { acc, item ->
            acc.plus(
                Article(
                    item,
                    Visual(item),
                    item.toLong(),
                    url = "https:://www.$item $item.com/liverpool"
                )
            )
        })
    }
}