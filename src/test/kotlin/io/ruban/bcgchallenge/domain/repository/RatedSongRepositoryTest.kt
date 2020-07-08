package io.ruban.bcgchallenge.domain.repository

import io.ruban.bcgchallenge.TestData
import io.ruban.bcgchallenge.domain.model.RatedSong
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*


class RatedSongRepositoryTest {

    private lateinit var datasource: SortedSet<RatedSong>

    private lateinit var unit: RatedSongRepository

    @BeforeEach
    fun `set up method`() {
        datasource = sortedSetOf(compareByDescending { it.gain })
        unit = RatedSongRepository(datasource)
        assertTrue(datasource.isEmpty())
    }

    @Test
    fun `should persist rated songs`() {
        val songs = listOf(
                TestData.randomRatedSong(),
                TestData.randomRatedSong()
        )

        unit.persist(songs)

        assertEquals(songs.size, datasource.size)
    }

    @Test
    fun `should get chunk of rated songs`() {
        val allSongs = (1..100).map { TestData.randomRatedSong() }.toList()
        datasource.addAll(allSongs)

        val chunked = unit.getChunk(78)
        assertTrue(chunked.isNotEmpty())
        assertEquals(78, chunked.size)
    }

    @Test
    fun `should get the most profitable song`() {
        val profitableSong = TestData.randomRatedSong(isrc = "ISRC_5", gain = 800.55)

        val allSongs = listOf(
                TestData.randomRatedSong(isrc = "ISRC_1", gain = 100.85),
                TestData.randomRatedSong(isrc = "ISRC_2", gain = 4.45),
                TestData.randomRatedSong(isrc = "ISRC_3", gain = 13.35),
                TestData.randomRatedSong(isrc = "ISRC_4", gain = 0.55),
                profitableSong,
                TestData.randomRatedSong(isrc = "ISRC_6", gain = 75.0)
        )
        datasource.addAll(allSongs)

        val topSong = unit.getChunk(1)

        assertTrue(topSong.isNotEmpty())
        assertEquals(listOf(profitableSong), topSong)
    }
}
