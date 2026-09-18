package com.hoijesse.donkeyassessment

import com.hoijesse.donkeyassessment.data.TimeLineEntry
import com.hoijesse.donkeyassessment.ui.TimeLineAdapter
import junit.framework.TestCase.assertTrue
import org.junit.Test

class TimeLineDiffCallBackTest {
    private val diffCallBack = TimeLineAdapter.DiffCallback

    @Test
    fun `areItemsTheSame returns true for entries with same id`() {
        val old = fakeEntry(1, text = "old")
        val new = fakeEntry(1, text = "new")
        assertTrue(diffCallBack.areItemsTheSame(old, new))
    }

    @Test
    fun `areItemsTheSame returns false for entries with different id`() {
        val old = fakeEntry(1, text = "old")
        val new = fakeEntry(2, text = "new")
        assertTrue(!diffCallBack.areItemsTheSame(old, new))
    }

    @Test
    fun `areContentsTheSame returns true for identical entries`() {
        val entry = fakeEntry(1, text = "text")
        assertTrue(diffCallBack.areContentsTheSame(entry, entry.copy()))
    }

    @Test
    fun `areContentsTheSame returns false when inGroup differs`() {
        val old = fakeEntry(1, inGroup = "old")
        val new = fakeEntry(1, inGroup = "new")
        assertTrue(!diffCallBack.areContentsTheSame(old, new))
    }

    @Test
    fun `areContentsTheSame returns false when text differs`() {
        val old = fakeEntry(1, text = "old")
        val new = fakeEntry(1, text = "new")
        assertTrue(!diffCallBack.areContentsTheSame(old, new))
    }

    @Test
    fun `areContentsTheSame returns false when image differs`() {
        val old = fakeEntry(1, image = "old")
        val new = fakeEntry(1, image = "new")
        assertTrue(!diffCallBack.areContentsTheSame(old, new))
    }

    @Test
    fun `areContentsTheSame returns false when senderName differs`() {
        val old = fakeEntry(1, senderName = "old")
        val new = fakeEntry(1, senderName = "new")
        assertTrue(!diffCallBack.areContentsTheSame(old, new))
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