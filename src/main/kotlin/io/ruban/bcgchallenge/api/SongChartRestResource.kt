package io.ruban.bcgchallenge.api

import io.ruban.bcgchallenge.domain.service.DataAggregator
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.OffsetDateTime


@RestController
class SongChartRestResource(
        private val dataAggregator: DataAggregator
) {

    @GetMapping("/songs/charts")
    fun getChart(@RequestParam(value = "top", defaultValue = "5") top: Int): ChartContainer {
        val chart = dataAggregator.generateChart(top)
        return ChartContainer(chart = chart, generatedAt = OffsetDateTime.now())
    }
}
