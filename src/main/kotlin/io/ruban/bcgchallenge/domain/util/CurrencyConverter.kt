package io.ruban.bcgchallenge.domain.util

// According to xe.com on 2020-07-07 19:28 UTC
private const val USD_EUR: Double = 0.886227
private const val GBP_EUR: Double = 1.11236

object CurrencyConverter {

    fun convertToEur(amount: Double, from: Currency): Double = when (from) {
        Currency.USD -> {
            amount * USD_EUR
        }
        Currency.GBP -> {
            amount * GBP_EUR
        }
        else -> amount
    }
}

enum class Currency {
    EUR, USD, GBP
}