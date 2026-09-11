package com.example.donkeyassessment

import com.example.donkeyassessment.data.TimeLineRepository
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Test

class TimeLineRepositoryTest {
    private val repo = TimeLineRepository()

    @Test
    fun getItems_twentyItems() {
        val items = repo.getItems(0, 20)
        assertEquals(20, items.size)
    }

    @Test
    fun getItems_offsetReturn() {
        val items = repo.getItems(20, 20)
        assertEquals(20, items.size)
    }

    @Test
    fun getItems_nearEndItems() {
        val items = repo.getItems(590, 20)
        assertEquals(10, items.size)
    }

    @Test
    fun getItems_afterEndEmpty() {
        val items = repo.getItems(600, 20)
        assertTrue(items.isEmpty())
    }

    @Test
    fun getItems_beyondEnd() {
        val items = repo.getItems(650, 20)
        assertTrue(items.isEmpty())
    }

    @Test(expected = IllegalArgumentException::class)
    fun getItems_negativeOffset() {
        repo.getItems(-1, 20)
    }

    @Test(expected = IllegalArgumentException::class)
    fun getItems_negativeLimit() {
        repo.getItems(20, -1)
    }

    @Test
    fun getItems_onlyReturnsGroupsOfCurrentUser() {
        val items = repo.getItems(
            offset = 0,
            limit = 100
        )

        val allowedGroups = listOf(
            "LINK",
            "Tim & Rank",
            "Gemeente"
        )

        assert(items.all { it.groupName in allowedGroups })
    }
}