package io.ruban.bcgchallenge

import io.ruban.bcgchallenge.domain.model.RatedSong
import io.ruban.bcgchallenge.domain.model.RatedSongView
import io.ruban.bcgchallenge.domain.model.ReportSong
import io.ruban.bcgchallenge.domain.util.Currency
import kotlin.random.Random

object TestData {

    fun randomRatedSongView(
            rank: Int = Random.nextInt(),
            isrc: String = Random.nextInt().toString(),
            trackName: String = Random.nextInt().toString(),
            artistName: String = Random.nextInt().toString(),
            gain: String = "${Random.nextInt()} ${Currency.values().random()}"
    ) = RatedSongView(
            rank = rank,
            isrc = isrc,
            trackName = trackName,
            artistName = artistName,
            gain = gain
    )

    fun randomRatedSong(
            isrc: String = Random.nextInt().toString(),
            trackName: String = Random.nextInt().toString(),
            artistName: String = Random.nextInt().toString(),
            gain: Double = Random.nextDouble()
    ) = RatedSong(
            isrc = isrc,
            trackName = trackName,
            artistName = artistName,
            gain = gain
    )

    fun randomReportSong(
            isrc: String = Random.nextInt().toString(),
            trackName: String = Random.nextInt().toString(),
            artistName: String = Random.nextInt().toString(),
            units: Long = Random.nextLong(),
            amount: Double = Random.nextDouble(),
            currency: Currency = Currency.values().random()
    ) = ReportSong(
            isrc = isrc,
            trackName = trackName,
            artistName = artistName,
            units = units,
            amount = amount,
            currency = currency
    )
}
