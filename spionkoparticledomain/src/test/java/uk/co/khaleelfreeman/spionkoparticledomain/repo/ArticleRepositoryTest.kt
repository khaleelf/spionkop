package uk.co.khaleelfreeman.spionkoparticledomain.repo

import io.reactivex.Single
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import uk.co.khaleelfreeman.spionkoparticledomain.SpionkopArticle
import uk.co.khaleelfreeman.spionkoparticledomain.service.NetworkService

class ArticleRepositoryTest {

    private val testNetworkService  =
        TestNetworkService()
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
        assertArrayEquals(expected.toTypedArray(), articleRepository.getArticles())
    }

    @Test
    fun `addFilter() should contain only articles that match the filter`() {
        articleRepository.addFilter("10 10")
        val expected = arrayOf(
            SpionkopArticle(
                title = "10",
                imageUrl = "10",
                date = "10",
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
            SpionkopArticle(
                title = "10",
                imageUrl = "10",
                date = "10",
                url = "https:://www.10 10.com/liverpool"
            ),
            SpionkopArticle(
                title = "9",
                imageUrl = "9",
                date = "9",
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
            SpionkopArticle(
                title = "9",
                imageUrl = "9",
                date = "9",
                url = "https:://www.9 9.com/liverpool"
            )
        )
        assert(articleRepository.getArticles().contentDeepEquals(expected))
    }
}

class TestNetworkService(val published : Long = 1578574582000) : NetworkService {

    val articles = generateTestArticles()

    override fun execute(): Single<Pair<Long, List<SpionkopArticle>>> {
        return Single.create<Pair<Long, List<SpionkopArticle>>> {
            it.onSuccess(
                Pair(published, articles)
            )
        }
    }

    private fun generateTestArticles(): List<SpionkopArticle> {
        return (1..10).map { it.toString() }.fold(emptyList(), { acc, item ->
            acc.plus(
                SpionkopArticle(
                    title = item,
                    imageUrl = item,
                    date = item,
                    url = "https:://www.$item $item.com/liverpool"
                )
            )
        })
    }
}