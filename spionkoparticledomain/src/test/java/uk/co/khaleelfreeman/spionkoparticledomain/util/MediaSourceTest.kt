package uk.co.khaleelfreeman.spionkoparticledomain.util

import org.junit.Assert.assertEquals
import org.junit.Test
import uk.co.khaleelfreeman.spionkoparticledomain.SpionkopArticle
import uk.co.khaleelfreeman.spionkoparticledomain.util.mediaSources

class MediaSourceTest {

    @Test
    fun `mediaSource() has only unique sources from articles`(){
        val articles = listOf(
            SpionkopArticle("https://www.liverpoolecho.co.uk/sport/football/football-news/steven-gerrard-what-annoyed-him-17538632","","",""),
            SpionkopArticle("https://www.theanfieldwrap.com/2020/01/january-liverpool-toughest-tests/","","",""),
            SpionkopArticle("https://www.liverpoolfc.com/news/first-team/381963-allan-joins-atletico-mineiro-on-permanent-deal","","",""),
            SpionkopArticle("https://www.empireofthekop.com/2020/01/09/liam-millar-returns-to-liverpool-after-kilmarnock-loan-is-cut-short/","","",""),
            SpionkopArticle("https://www.liverpoolecho.co.uk/sport/football/football-news/steven-gerrard-what-annoyed-him-17538632","","","")
        )
        val mediaSources = mediaSources(articles)
        val expectedSources = setOf("liverpoolecho", "theanfieldwrap", "liverpoolfc", "empireofthekop")
        assertEquals(mediaSources, expectedSources)
    }
}