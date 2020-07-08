package io.ruban.bcgchallenge.domain.exception

data class CsvParseException(val msg: String) : RuntimeException(msg)