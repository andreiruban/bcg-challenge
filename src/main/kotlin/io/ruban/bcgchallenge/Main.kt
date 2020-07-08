package io.ruban.bcgchallenge

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class BcgChallengeApplication

fun main(args: Array<String>) {
    runApplication<BcgChallengeApplication>(*args)
}
