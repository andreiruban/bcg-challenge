package io.ruban.bcgchallenge.domain.service

import io.ruban.bcgchallenge.domain.repository.ReportSongRepository
import io.ruban.bcgchallenge.domain.util.CsvParser
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.io.support.ResourcePatternResolver
import org.springframework.stereotype.Service
import java.io.File

private const val CSV_EXTENSION = ".csv"

@Service
class ReportBatcher(
        private val resourceResolver: ResourcePatternResolver,
        private val csvParser: CsvParser,
        private val repository: ReportSongRepository
) {
    private val log: Logger = LoggerFactory.getLogger(ReportBatcher::class.java)

    private val path = "classpath:reports/*${CSV_EXTENSION}"

    private val batched: MutableSet<String> = HashSet()

    fun batchReportFolder() {
        val file: File? = retrieveNewFile(path)
        file?.run {
            log.info("Processing report: ${file.name}")
            val parsed = csvParser.parseReport(file)
            repository.persist(parsed)
            batched.add(this.name)
        }
    }

    private fun retrieveNewFile(path: String): File? =
            resourceResolver.getResources(path).filter { it.isFile }.firstOrNull { !batched.contains(it.filename) }?.file
}
