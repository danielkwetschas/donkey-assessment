package com.example.donkeyassessment.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.donkeyassessment.data.TimeLineItem
import com.example.donkeyassessment.data.TimeLineRepository

class TimeLineViewModel(
    private val repo: TimeLineRepository = TimeLineRepository()
) : ViewModel() {
    private val pageSize = 20
    private var isLoading = false
    private var hasMoreItems = true
    private val _items = MutableLiveData<List<TimeLineItem>>(emptyList())
    val items: LiveData<List<TimeLineItem>> = _items

    fun firstPage() {
        if (_items.value?.isNotEmpty() == true) return
        nextPage()
    }

    fun nextPage(){
        if (isLoading || !hasMoreItems) return

        isLoading = true

        val currentItems = _items.value.orEmpty()
        val nextItems = repo.getItems(currentItems.size, pageSize)

        if(nextItems.isEmpty()) {
            hasMoreItems = false
        } else {
            _items.value = currentItems + nextItems
        }

        isLoading = false
    }
}