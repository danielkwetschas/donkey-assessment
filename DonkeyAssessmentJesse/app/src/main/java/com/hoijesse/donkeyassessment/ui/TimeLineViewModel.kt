package com.hoijesse.donkeyassessment.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hoijesse.donkeyassessment.data.TimeLineEntry
import com.hoijesse.donkeyassessment.data.TimeLineRepository

class TimeLineViewModel(
    private val repository: TimeLineRepository
) : ViewModel() {
    private val pageSize: Int = 20
    private val _entries = MutableLiveData<List<TimeLineEntry>>(emptyList())
    val entries = _entries
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading
    private val currentEntries = mutableListOf<TimeLineEntry>()
    private var hasReachedEnd = false

    fun initPage() {
        if (_entries.value?.isNotEmpty() == true) return
        nextPage()
    }

    fun nextPage() {
        if (_isLoading.value == true || hasReachedEnd) return
        _isLoading.value = true

        val pageStart = currentEntries.size
        val pageEntries = repository.getEntries(pageStart, pageSize)

        if (pageEntries.isEmpty()) {
            hasReachedEnd = true
        } else {
            currentEntries.addAll(pageEntries)
            _entries.value = currentEntries.toList()
        }

        _isLoading.value = false
    }
}