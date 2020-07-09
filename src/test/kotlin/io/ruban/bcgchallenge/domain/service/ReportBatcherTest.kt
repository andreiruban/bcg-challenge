package io.ruban.bcgchallenge.domain.service

import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
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
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
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

    val expectedUSReport = listOf(
            ReportSong(
                    isrc = "0462407301Z2",
                    trackName = "Whitney",
                    artistName = "Johnny Cash",
                    units = 5,
                    amount = 0.63,
                    currency = Currency.USD
            ),
            ReportSong(
                    isrc = "O799Z00455L2",
                    trackName = "Falling into You",
                    artistName = "Duke Ellington",
                    units = 6,
                    amount = 0.63,
                    currency = Currency.USD
            ),
            ReportSong(
                    isrc = "4201553WEC56",
                    trackName = "25",
                    artistName = "Cat Anderson",
                    units = 6,
                    amount = 0.70,
                    currency = Currency.USD
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
    fun `should process report files one by one in folder`() {
        unit.batchReportFolder()
        unit.batchReportFolder()

        val parsedCaptor = argumentCaptor<List<ReportSong>>()

        verify(repository, times(2)).persist(parsedCaptor.capture())

        val parsedUKSongs = parsedCaptor.firstValue
        assertTrue(parsedUKSongs.isNotEmpty())
        assertEquals(expectedUKReport, parsedUKSongs)

        val parsedUSSongs = parsedCaptor.secondValue
        assertTrue(parsedUSSongs.isNotEmpty())
        assertEquals(expectedUSReport, parsedUSSongs)
    }
}
