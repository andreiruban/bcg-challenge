package io.ruban.bcgchallenge.domain.model

import io.ruban.bcgchallenge.domain.util.Currency

data class ReportSong(
        val isrc: String,
        val trackName: String,
        val artistName: String,
        val units: Long,
        val amount: Double,
        val currency: Currency
)
