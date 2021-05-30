package com.rodrigocibils.jobsity.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rodrigocibils.jobsity.R
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
                disableEndlessScrolling()
                page += 1
                presenter.getShows(page)
            }
        }
    }

    private fun disableEndlessScrolling() {
        binding.mainActivityRecyclerView.removeOnScrollListener(scrollListener)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = MainPresenter(this, shows)

        setupRecyclerView()
        presenter.getShows(page)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_bar, menu)
        val searchBar = menu?.findItem(R.id.appSearchBar)
        searchBar?.let {
            val searchView = it.actionView as SearchView
            searchView.queryHint = resources.getString(R.string.search_hint)
            searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let {
                        disableEndlessScrolling()
                        presenter.getShows(query)
                    }
                    return false
                }

                override fun onQueryTextChange(query: String?): Boolean {
                    if(query == null || query == "") {
                        resetEndlessScrolling()
                        page = 0
                        presenter.getShows(page)
                    }
                    return true
                }
            })
        }

        return super.onCreateOptionsMenu(menu)
    }

    private fun setupRecyclerView() {
        binding.mainActivityRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = ShowsAdapter(shows, ::goToShowInfo)
        }
        binding.mainActivityRecyclerView.addOnScrollListener(scrollListener)
    }

    private fun goToShowInfo(show: Show) {
        val intent = Intent(this, ShowActivity::class.java)
        intent.putExtra("show", show)
        startActivity(intent)
    }

    private fun hideAll() {
        binding.mainActivityNoResultsLayout.visibility = View.GONE
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

    override fun showNoResults() {
        hideAll()
        binding.mainActivityNoResultsLayout.visibility = View.VISIBLE
    }

    private fun resetEndlessScrolling() {
        binding.mainActivityRecyclerView.addOnScrollListener(scrollListener)
    }
}