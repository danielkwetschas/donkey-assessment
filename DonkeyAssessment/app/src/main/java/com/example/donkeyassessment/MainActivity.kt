package com.example.donkeyassessment

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.donkeyassessment.data.weather.WeatherCodeMapper
import com.example.donkeyassessment.ui.TimeLineViewModel
import com.example.donkeyassessment.ui.WeatherViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: TimeLineViewModel by viewModels()
    private val weatherViewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Weather
        val weatherTemperatureTextView = findViewById<TextView>(R.id.weatherTemperatureTextView)
        val weatherDescriptionTextView = findViewById<TextView>(R.id.weatherDescriptionTextView)

        // Timeline
        val timeLineRecyclerView = findViewById<RecyclerView>(R.id.timelineRecyclerView)

        val timeLineAdapter = TimeLineAdapter()
        val layoutManager = LinearLayoutManager(this)

        timeLineRecyclerView.layoutManager = layoutManager
        timeLineRecyclerView.adapter = timeLineAdapter

        viewModel.items.observe(this) { items ->
            timeLineAdapter.submitList(items)
        }
        viewModel.firstPage()

        timeLineRecyclerView.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                    // Load the next page when user approaches end of current list
                    if (dy > 0 && lastVisibleItem >= timeLineAdapter.itemCount - 5) {
                        viewModel.nextPage()
                    }
                }
            }
        )

        // Weather
        weatherViewModel.temperature.observe(this) { temperature ->
            weatherTemperatureTextView.text = "$temperature °C"
        }

        weatherViewModel.weatherCode.observe(this) { weatherCode ->
            weatherDescriptionTextView.text = WeatherCodeMapper.getDescription(weatherCode)

        }

        weatherViewModel.error.observe(this) { error ->
            if (error) {
                weatherTemperatureTextView.text = "Weather unavailable"
                weatherDescriptionTextView.text = ""
            }
        }

        weatherViewModel.loadWeather()
    }
}