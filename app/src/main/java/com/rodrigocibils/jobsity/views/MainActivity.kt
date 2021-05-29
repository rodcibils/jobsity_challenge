package com.rodrigocibils.jobsity.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.rodrigocibils.jobsity.R
import com.rodrigocibils.jobsity.adapters.ShowsAdapter
import com.rodrigocibils.jobsity.contracts.MainContract
import com.rodrigocibils.jobsity.databinding.ActivityMainBinding
import com.rodrigocibils.jobsity.models.Show
import com.rodrigocibils.jobsity.presenters.MainPresenter

class MainActivity : AppCompatActivity(), MainContract.ViewContract {

    private lateinit var binding: ActivityMainBinding

    private lateinit var presenter: MainContract.PresenterContract

    private val shows = mutableListOf<Show>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = MainPresenter(this, shows)

        setupRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        presenter.searchShows(0)
    }

    private fun setupRecyclerView() {
        binding.mainActivityRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = ShowsAdapter(shows)
        }
    }

    override fun updateRecyclerView() {
        binding.mainActivityRecyclerView.adapter?.notifyDataSetChanged()
    }

    override fun showError() {
        binding.mainActivityErrorLayout.visibility = View.VISIBLE
    }
}