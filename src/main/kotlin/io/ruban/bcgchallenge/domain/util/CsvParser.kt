package io.ruban.bcgchallenge.domain.util

import com.github.doyaaaaaken.kotlincsv.client.CsvReader
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import io.ruban.bcgchallenge.domain.exception.CsvParseException
import io.ruban.bcgchallenge.domain.model.ReportSong
import org.springframework.stereotype.Component
import java.io.File
import javax.annotation.PostConstruct

private const val ISRC_HEADER = "ISRC"
private const val TRACK_NAME_HEADER = "TRACK_NAME"
private const val ARTIST_NAME_HEADER = "ARTIST_NAME"
private const val UNITS_HEADER = "UNITS"
private const val AMOUNT_HEADER = "AMOUNT"
private const val CURRENCY_HEADER = "CURRENCY"

@Component
class CsvParser {

    private lateinit var csvReader: CsvReader

    @PostConstruct
    fun setUp() {
        csvReader = csvReader {
            quoteChar = '"'
            delimiter = ';'
            escapeChar = '"'
            skipEmptyLine = true
            skipMissMatchedRow = false
        }
    }

    fun parseReport(file: File): List<ReportSong> =
            csvReader.readAllWithHeader(file).map {
                ReportSong(
                        isrc = it[ISRC_HEADER] ?: throw CsvParseException("Unable to parse $ISRC_HEADER"),
                        trackName = it[TRACK_NAME_HEADER]
                                ?: throw CsvParseException("Unable to parse $TRACK_NAME_HEADER"),
                        artistName = it[ARTIST_NAME_HEADER]
                                ?: throw CsvParseException("Unable to parse $ARTIST_NAME_HEADER"),
                        units = it[UNITS_HEADER]?.toLong() ?: throw CsvParseException("Unable to parse $UNITS_HEADER"),
                        amount = it[AMOUNT_HEADER]?.toDouble()
                                ?: throw CsvParseException("Unable to parse $AMOUNT_HEADER"),
                        currency = Currency.valueOf(it[CURRENCY_HEADER]
                                ?: throw CsvParseException("Unable to parse $CURRENCY_HEADER"))
                )
            }
}
