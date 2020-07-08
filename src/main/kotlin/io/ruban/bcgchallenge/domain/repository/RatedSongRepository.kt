package io.ruban.bcgchallenge.domain.repository

import io.ruban.bcgchallenge.domain.model.RatedSong
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class RatedSongRepository(
        val datasource: SortedSet<RatedSong> = sortedSetOf(compareByDescending { it.gain })
) {

    fun persist(new: List<RatedSong>) = datasource.addAll(new)

    fun getChunk(chunk: Int): List<RatedSong> = datasource.take(chunk)
}
