package io.ruban.bcgchallenge.domain.service

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.ruban.bcgchallenge.TestData
import io.ruban.bcgchallenge.domain.model.RatedSong
import io.ruban.bcgchallenge.domain.model.RatedSongView
import io.ruban.bcgchallenge.domain.repository.RatedSongRepository
import io.ruban.bcgchallenge.domain.repository.ReportSongRepository
import io.ruban.bcgchallenge.domain.util.Currency
import io.ruban.bcgchallenge.domain.util.GBP_EUR
import io.ruban.bcgchallenge.domain.util.USD_EUR
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DataAggregatorTest {

    private val reportRepository: ReportSongRepository = mock()
    private val ratedRepository: RatedSongRepository = mock()

    private lateinit var unit: DataAggregator

    @BeforeEach
    fun `set up method`() {
        unit = DataAggregator(
                reportSongRepository = reportRepository,
                ratedSongRepository = ratedRepository
        )
    }

    @Test
    fun `should handle reports`() {
        val testReportUS = TestData.randomReportSong(units = 130, amount = 100.0, currency = Currency.USD)
        val testReportUK = TestData.randomReportSong(units = 270, amount = 100.0, currency = Currency.GBP)

        whenever(reportRepository.getChunkAndDelete(eq(2))).thenReturn(listOf(testReportUS, testReportUK))
        whenever(ratedRepository.persist(any())).thenReturn(true)

        val captor = argumentCaptor<List<RatedSong>>()

        unit.handleReports(chunk = 2)
        verify(ratedRepository).persist(captor.capture())

        val captured = captor.firstValue
        assertTrue(captured.isNotEmpty())
        assertEquals(
                listOf(
                        RatedSong(
                                isrc = testReportUS.isrc,
                                artistName = testReportUS.artistName,
                                trackName = testReportUS.trackName,
                                gain = testReportUS.amount * testReportUS.units * USD_EUR
                        ),
                        RatedSong(
                                isrc = testReportUK.isrc,
                                artistName = testReportUK.artistName,
                                trackName = testReportUK.trackName,
                                gain = testReportUK.amount * testReportUK.units * GBP_EUR
                        )
                ), captured
        )
    }

    @Test
    fun `should generate a chart`() {
        val song1 = TestData.randomRatedSong(gain = 30.55)
        val song2 = TestData.randomRatedSong(gain = 130.55)
        val song3 = TestData.randomRatedSong(gain = 1.55)
        whenever(ratedRepository.getChunk(eq(3))).thenReturn(listOf(song2, song1, song3))

        val chart: List<RatedSongView> = unit.generateChart(3)
        assertTrue(chart.isNotEmpty())
        assertEquals(
                listOf(
                        RatedSongView(
                                rank = 1,
                                isrc = song2.isrc,
                                trackName = song2.trackName,
                                artistName = song2.artistName,
                                gain = "${String.format("%.2f", song2.gain)} ${Currency.EUR.name}"
                        ),
                        RatedSongView(
                                rank = 2,
                                isrc = song1.isrc,
                                trackName = song1.trackName,
                                artistName = song1.artistName,
                                gain = "${String.format("%.2f", song1.gain)} ${Currency.EUR.name}"
                        ),
                        RatedSongView(
                                rank = 3,
                                isrc = song3.isrc,
                                trackName = song3.trackName,
                                artistName = song3.artistName,
                                gain = "${String.format("%.2f", song3.gain)} ${Currency.EUR.name}"
                        )
                ),
                chart
        )
    }
}
