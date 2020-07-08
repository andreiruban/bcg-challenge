package io.ruban.bcgchallenge.domain.model

data class RatedSong(
        val isrc: String,
        val trackName: String,
        val artistName: String,
        val gain: Double
)

data class RatedSongView(
        val rank: Int,
        val isrc: String,
        val trackName: String,
        val artistName: String,
        val gain: String
)