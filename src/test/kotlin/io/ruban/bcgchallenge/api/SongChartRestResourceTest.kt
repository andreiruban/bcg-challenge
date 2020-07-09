package io.ruban.bcgchallenge.api

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.ruban.bcgchallenge.TestData
import io.ruban.bcgchallenge.domain.service.DataAggregator
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SongChartRestResourceTest {

    private val dataAggregator: DataAggregator = mock()

    private lateinit var unit: SongChartRestResource

    @BeforeEach
    fun `set up method`() {
        unit = SongChartRestResource(
                dataAggregator = dataAggregator
        )
    }

    @Test
    fun `should generate a song chart`() {
        val songViews = listOf(
                TestData.randomRatedSongView(),
                TestData.randomRatedSongView(),
                TestData.randomRatedSongView()
        )

        whenever(dataAggregator.generateChart(any())).thenReturn(songViews)

        val chart = unit.getChart(3)
        Assertions.assertEquals(songViews, chart.chart)
    }
}
