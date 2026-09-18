package com.hoijesse.donkeyassessment

import com.hoijesse.donkeyassessment.data.TimeLineRepository
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Test

class TimeLineRepositoryTest {
    private val repository = TimeLineRepository()

    @Test
    fun `get 20 entries`() {
        val entries = repository.getEntries(0, 20)
        assertEquals(20, entries.size)
    }

    @Test
    fun `get with offset start`() {
        val entries = repository.getEntries(100, 20)
        assertEquals(20, entries.size)
    }

    @Test
    fun `get approaching end`() {
        val entries = repository.getEntries(1190, 20)
        assertEquals(10, entries.size)
    }

    @Test
    fun `get empty`() {
        val entries = repository.getEntries(1200, 20)
        assertTrue(entries.isEmpty())
    }

    @Test(expected = IllegalArgumentException::class)
    fun `get with negative offset start`() {
        repository.getEntries(-10, 20)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `get with negative entry count`() {
        repository.getEntries(0, -1)
    }

    @Test
    fun `exclusively user groups`() {
        val entries = repository.getEntries(0, 255)
        val userGroups = listOf(
            "Main",
            "Bijbeltekst",
            "Klussen",
            "Oeps"
        )
        assert(entries.all { it.inGroup in userGroups })
    }
}