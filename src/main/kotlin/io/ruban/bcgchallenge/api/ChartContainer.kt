package io.ruban.bcgchallenge.api

import io.ruban.bcgchallenge.domain.model.RatedSongView
import java.time.OffsetDateTime

data class ChartContainer(
        val chart: List<RatedSongView>,
        val generatedAt: OffsetDateTime
)