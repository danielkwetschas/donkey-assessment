package com.hoijesse.donkeyassessment

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.hoijesse.donkeyassessment.data.TimeLineEntry
import com.hoijesse.donkeyassessment.ui.TimeLineAdapter
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowLooper
import java.util.concurrent.TimeUnit

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class TimeLineAdapterTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private var adapter = TimeLineAdapter()

    @Test
    fun `submitList updates itemCount`() {
        val entries = listOf(fakeEntry(1), fakeEntry(2), fakeEntry(3))
        submitListAndAwait(entries)
        assertEquals(3, adapter.itemCount)
    }

    @Test
    fun `submitList with empty list results in zero itemCount`() {
        submitListAndAwait(listOf(fakeEntry(1)))
        submitListAndAwait(emptyList())
        assertEquals(0, adapter.itemCount)
    }

    @Test
    fun `getItem returns correct item`() {
        val entries = listOf(fakeEntry(1, text = "text1"), fakeEntry(2, text = "text2"))
        submitListAndAwait(entries)
        assertEquals("text1", adapter.getEntryAt(0).text)
        assertEquals("text2", adapter.getEntryAt(1).text)
    }

    private fun submitListAndAwait(entries: List<TimeLineEntry>) {
        var finished = false
        adapter.submitList(entries) {
            finished = true
        }
        
        val start = System.currentTimeMillis()
        while (!finished && System.currentTimeMillis() - start < 2000) {
            ShadowLooper.idleMainLooper()
            Thread.sleep(10)
        }
        assertTrue("submitList did not complete in time", finished)
    }

    private fun fakeEntry(
        id: Int,
        inGroup: String = "group",
        text: String = "text",
        image: String? = null,
        senderName: String = "sender"
    ) = TimeLineEntry(
        id = id,
        inGroup = inGroup,
        text = text,
        image = image,
        senderName = senderName
    )
}