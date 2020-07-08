package io.ruban.bcgchallenge.domain.repository

import io.ruban.bcgchallenge.TestData
import io.ruban.bcgchallenge.domain.model.ReportSong
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*


class ReportSongRepositoryTest {

    private lateinit var datasource: MutableList<ReportSong>

    private lateinit var unit: ReportSongRepository

    @BeforeEach
    fun `set up method`() {
        datasource = LinkedList()
        unit = ReportSongRepository(datasource)
        assertTrue(datasource.isEmpty())
    }

    @Test
    fun `should persist report songs`() {
        val reports = listOf(
                TestData.randomReportSong(),
                TestData.randomReportSong()
        )

        unit.persist(reports)

        assertEquals(reports.size, datasource.size)
    }

    @Test
    fun `should take a chunk of report songs`() {
        val reports = (1..100).map { TestData.randomReportSong() }.toList()
        datasource.addAll(reports)

        val chunked = unit.getChunkAndDelete(chunk = 65)
        assertTrue(chunked.isNotEmpty())
        assertEquals(65, chunked.size)

        assertEquals(reports.size - 65, datasource.size)
    }
}
