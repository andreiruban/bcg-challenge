package io.ruban.bcgchallenge.domain.repository

import io.ruban.bcgchallenge.domain.model.ReportSong
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class ReportSongRepository(
        val datasource: MutableList<ReportSong> = LinkedList()
) {
    fun persist(new: List<ReportSong>) = datasource.addAll(new)

    fun getChunkAndDelete(chunk: Int): List<ReportSong> {
        val chunked = datasource.take(chunk)
        datasource.removeAll(chunked)
        return chunked
    }
}
