package io.ruban.bcgchallenge.job

import io.ruban.bcgchallenge.domain.service.DataAggregator
import io.ruban.bcgchallenge.domain.service.ReportBatcher
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class Jobs(
        val reportBatcher: ReportBatcher,
        val dataAggregator: DataAggregator
) {

    @Scheduled(initialDelay = 0, fixedRate = 500)
    fun batchReports() {
        reportBatcher.batchReportFolder()
    }

    @Scheduled(initialDelay = 100, fixedRate = 500)
    fun aggregateData() {
        dataAggregator.handleReports(chunk = 10_000)
    }
}
