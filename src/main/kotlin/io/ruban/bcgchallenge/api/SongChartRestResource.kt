package io.ruban.bcgchallenge.api

import io.ruban.bcgchallenge.domain.service.DataAggregator
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.time.OffsetDateTime


@RestController
class SongChartRestResource(
        private val dataAggregator: DataAggregator
) {

    @GetMapping("/songs/charts?top={top}")
    fun getChart(@PathVariable("top") top: Int): ChartContainer {
        val chart = dataAggregator.generateChart(top)
        return ChartContainer(chart = chart, generatedAt = OffsetDateTime.now())
    }
}
