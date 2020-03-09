package uk.co.khaleelfreeman.spionkoparticledomain.repo

import io.reactivex.Single
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
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
        articleRepository.fetchArticles().blockingGet()
    }

    @Test
    fun `getSources() strips out the domain name from the url and returns a unique set`() {
        val expectedSources = (1..10).map { "$it $it" }.toSet() // a source will look like "1 1" according to how the TestNetworkService is setup.
        assertThat(articleRepository.getSources(), `is`(expectedSources))
    }

    @Test
    fun `getPublished() returns the published time from the service`() {
        val expected = testNetworkService.published
        assertThat(expected, `is`(articleRepository.published))
    }

    @Test
    fun `getArticles() should return the articles from the service`() {
        val expected = testNetworkService.articles
        assertThat(expected, `is`(articleRepository.getArticles()))
    }

    @Test
    fun `addFilter() should contain only articles that match the filter`() {
        articleRepository.addFilter("10 10")
        val expected: List<SpionkopArticle> = listOf(
            SpionkopArticle(
                title = "10",
                imageUrl = "10",
                date = "10",
                url = "https:://www.10 10.com/liverpool"
            )
        )
        assertThat(articleRepository.getArticles(), `is`(expected))
    }

    @Test
    fun `addFilter() called with different filters should return article list with multiple articles`() {
        articleRepository.addFilter("10 10")
        articleRepository.addFilter("9 9")
        val expected = listOf(
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
        assertThat(articleRepository.getArticles(), `is`(expected))
    }

    @Test
    fun `removeFilter() should remove articles from the filtered list`() {
        articleRepository.addFilter("10 10")
        articleRepository.addFilter("9 9")
        articleRepository.removeFilter("10 10")
        val expected = listOf(
            SpionkopArticle(
                title = "9",
                imageUrl = "9",
                date = "9",
                url = "https:://www.9 9.com/liverpool"
            )
        )
        assertThat(articleRepository.getArticles(), `is`(expected))
    }
}

class TestNetworkService(val published : Long = 1578574582000) : NetworkService {

    val articles = generateTestArticles()

    override fun execute(): Single<Pair<Long, List<SpionkopArticle>>> {
        return Single.create {
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