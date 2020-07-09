package io.ruban.bcgchallenge.domain.service

import io.ruban.bcgchallenge.domain.repository.ReportSongRepository
import io.ruban.bcgchallenge.domain.util.CsvParser
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
    private val path = "classpath:reports/*${CSV_EXTENSION}"

    private val batched: MutableSet<String> = HashSet()

    fun batchReportFolder() {
        val file = retrieveNewFile(path)
        file?.run {
            val parsed = csvParser.parseReport(file)
            repository.persist(parsed)
            batched.add(this.name)
        }
    }

    private fun retrieveNewFile(path: String): File? =
            resourceResolver.getResources(path).filter { it.isFile }.first { !batched.contains(it.filename) }.file
}
