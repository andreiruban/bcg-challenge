package io.ruban.bcgchallenge.job

import io.ruban.bcgchallenge.domain.service.ReportBatcher
import io.ruban.bcgchallenge.domain.service.DataAggregator
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class Jobs(
        val reportBatcher: ReportBatcher,
        val dataAggregator: DataAggregator
) {

    @Scheduled(fixedRate = 1000)
    fun batchReports() {
        reportBatcher.batchReportFolder()
    }

    @Scheduled(fixedRate = 1000)
    fun aggregateData() {
        dataAggregator.rateSongs(chunk = 10_000)
    }
}