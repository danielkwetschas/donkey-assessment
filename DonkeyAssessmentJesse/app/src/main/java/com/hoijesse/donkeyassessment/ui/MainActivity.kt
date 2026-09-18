package com.hoijesse.donkeyassessment.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hoijesse.donkeyassessment.R
import com.hoijesse.donkeyassessment.data.TimeLineRepository

class MainActivity : AppCompatActivity() {
    private val viewModel: TimeLineViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return TimeLineViewModel(TimeLineRepository()) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val timeLine = findViewById<RecyclerView>(R.id.timeLine)
        val timeLineAdapter = TimeLineAdapter()
        timeLine.layoutManager = LinearLayoutManager(this)
        timeLine.adapter = timeLineAdapter

        viewModel.entries.observe(this) { entries ->
            timeLineAdapter.submitList(entries)
        }

        viewModel.initPage()

        infiniteScrollSetup(timeLine)
    }

    private fun infiniteScrollSetup(timeLine: RecyclerView) {
        val layoutManager = timeLine.layoutManager as LinearLayoutManager

        timeLine.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

                if (dy > 0 && lastVisibleItemPosition >= layoutManager.itemCount - 5) viewModel.nextPage()
            }
        }
        )
    }
}