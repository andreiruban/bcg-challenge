package io.ruban.bcgchallenge.domain.service

import io.ruban.bcgchallenge.domain.model.RatedSong
import io.ruban.bcgchallenge.domain.model.RatedSongView
import io.ruban.bcgchallenge.domain.model.ReportSong
import io.ruban.bcgchallenge.domain.repository.RatedSongRepository
import io.ruban.bcgchallenge.domain.repository.ReportSongRepository
import io.ruban.bcgchallenge.domain.util.Currency
import io.ruban.bcgchallenge.domain.util.CurrencyConverter.convertToEur
import org.springframework.stereotype.Service

@Service
class DataAggregator(
        private val reportSongRepository: ReportSongRepository,
        private val ratedSongRepository: RatedSongRepository
) {

    fun generateChart(top: Int): List<RatedSongView> {
        val charted: List<RatedSong> = ratedSongRepository.getChunk(chunk = top)
        return mapChartedToView(charted)
    }

    fun handleReports(chunk: Int) {
        val reported: List<ReportSong> = reportSongRepository.getChunkAndDelete(chunk)
        val rated: List<RatedSong> = mapReport(reported)
        ratedSongRepository.persist(rated)
    }

    private fun mapChartedToView(charted: List<RatedSong>): List<RatedSongView> = charted.mapIndexed { index, song ->
        RatedSongView(
                rank = index + 1,
                isrc = song.isrc,
                trackName = song.trackName,
                artistName = song.artistName,
                gain = mapGain(song.gain)
        )
    }

    private fun mapGain(amount: Double): String = "${String.format("%.2f", amount)} ${Currency.EUR.name}"

    private fun mapReport(reported: List<ReportSong>): List<RatedSong> = reported.map {
        RatedSong(
                isrc = it.isrc,
                trackName = it.trackName,
                artistName = it.artistName,
                gain = calculateGain(it)
        )
    }

    private fun calculateGain(report: ReportSong): Double {
        val amountInEur = convertToEur(amount = report.amount, from = report.currency)
        return amountInEur * report.units
    }
}
