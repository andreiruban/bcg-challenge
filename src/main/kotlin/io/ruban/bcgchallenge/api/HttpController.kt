package io.ruban.bcgchallenge.api

import io.ruban.bcgchallenge.domain.model.RatedSongView
import io.ruban.bcgchallenge.domain.service.DataAggregator
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class HttpController(
        private val dataAggregator: DataAggregator
) {

    @GetMapping("/")
    fun hello() = "Hello!"

    @GetMapping("/songs/charts")
//    fun getChart(@PathVariable("top") top: Int): List<RatedSongView> {
    fun getChart(): List<RatedSongView> {
        return dataAggregator.generateChart(5)
    }
}
