package io.ruban.bcgchallenge.domain.util

const val USD_EUR: Double = 0.89
const val GBP_EUR: Double = 1.12

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