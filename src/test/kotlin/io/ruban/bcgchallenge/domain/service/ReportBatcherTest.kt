package io.ruban.bcgchallenge.domain.service

import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.ruban.bcgchallenge.domain.model.ReportSong
import io.ruban.bcgchallenge.domain.repository.ReportSongRepository
import io.ruban.bcgchallenge.domain.util.CsvParser
import io.ruban.bcgchallenge.domain.util.Currency
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.support.ResourcePatternResolver

@SpringBootTest
class ReportBatcherTest {

    val expectedUKReport = listOf(
            ReportSong(
                    isrc = "65037E030309",
                    trackName = "Thriller",
                    artistName = "The Slits",
                    units = 3,
                    amount = 0.32,
                    currency = Currency.GBP
            ),
            ReportSong(
                    isrc = "Z349S33869V0",
                    trackName = "Backstreet's Back",
                    artistName = "George Michael",
                    units = 5,
                    amount = 0.50,
                    currency = Currency.GBP
            ),
            ReportSong(
                    isrc = "4364W633B3P5",
                    trackName = "Gold: Greatest Hits",
                    artistName = "Michael Jackson",
                    units = 1,
                    amount = 0.54,
                    currency = Currency.GBP
            )
    )

    @Autowired
    private lateinit var resourseLoader: ResourcePatternResolver

    @Autowired
    private lateinit var csvParser: CsvParser

    private val repository: ReportSongRepository = mock()

    private lateinit var unit: ReportBatcher


    @BeforeEach
    fun `set up method`() {
        unit = ReportBatcher(
                resourceResolver = resourseLoader,
                csvParser = csvParser,
                repository = repository
        )
    }

    @Test
    fun `should process first file in folder`() {

        unit.batchReportFolder()

        val parsedCaptor = argumentCaptor<List<ReportSong>>()
        verify(repository).persist(parsedCaptor.capture())

        val parsedSongs = parsedCaptor.firstValue
        assertTrue(parsedSongs.isNotEmpty())
        assertEquals(expectedUKReport, parsedSongs)
    }
}
