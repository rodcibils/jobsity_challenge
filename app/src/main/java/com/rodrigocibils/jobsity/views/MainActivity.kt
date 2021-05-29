package com.rodrigocibils.jobsity.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rodrigocibils.jobsity.adapters.ShowsAdapter
import com.rodrigocibils.jobsity.contracts.MainContract
import com.rodrigocibils.jobsity.databinding.ActivityMainBinding
import com.rodrigocibils.jobsity.models.Show
import com.rodrigocibils.jobsity.presenters.MainPresenter

class MainActivity : AppCompatActivity(), MainContract.ViewContract {

    object MainActivityConstants {
        const val INFINITE_SCROLLING_THRESHOLD = 30
    }

    private lateinit var binding: ActivityMainBinding

    private lateinit var presenter: MainContract.PresenterContract

    private val shows = mutableListOf<Show>()

    private var page = 0

    private val scrollListener = object: RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            val manager = binding.mainActivityRecyclerView.layoutManager as LinearLayoutManager
            val count = manager.itemCount
            val lastVisible = manager.findLastVisibleItemPosition()
            if(count > 0 && lastVisible > count - MainActivityConstants.INFINITE_SCROLLING_THRESHOLD) {
                binding.mainActivityRecyclerView.removeOnScrollListener(this)
                page += 1
                presenter.searchShows(page)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = MainPresenter(this, shows)

        setupRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        presenter.searchShows(page)
    }

    private fun setupRecyclerView() {
        binding.mainActivityRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = ShowsAdapter(shows)
        }
        binding.mainActivityRecyclerView.addOnScrollListener(scrollListener)
    }

    private fun hideAll() {
        binding.mainActivityErrorLayout.visibility = View.GONE
        binding.mainActivityLoadingLayout.visibility = View.GONE
    }

    override fun updateRecyclerView() {
        hideAll()
        if(page != 0 && shows.last().id < 0) {
            resetEndlessScrolling()
        }
        binding.mainActivityRecyclerView.adapter?.notifyDataSetChanged()
    }

    override fun showError() {
        hideAll()
        binding.mainActivityErrorLayout.visibility = View.VISIBLE
    }

    override fun showLoading() {
        hideAll()
        binding.mainActivityLoadingLayout.visibility = View.VISIBLE
    }

    private fun resetEndlessScrolling() {
        binding.mainActivityRecyclerView.addOnScrollListener(scrollListener)
    }
}