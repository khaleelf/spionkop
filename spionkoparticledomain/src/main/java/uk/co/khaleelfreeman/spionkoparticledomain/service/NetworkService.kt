package uk.co.khaleelfreeman.spionkoparticledomain.service

import io.reactivex.Single
import uk.co.khaleelfreeman.spionkoparticledomain.SpionkopArticle

interface NetworkService {
    fun execute(): Single<Pair<Long, List<SpionkopArticle>>>
}