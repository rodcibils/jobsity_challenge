package com.rodrigocibils.jobsity.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.rodrigocibils.jobsity.R
import com.rodrigocibils.jobsity.adapters.EpisodesAdapter
import com.rodrigocibils.jobsity.contracts.ShowContract
import com.rodrigocibils.jobsity.databinding.ActivityShowBinding
import com.rodrigocibils.jobsity.models.Episode
import com.rodrigocibils.jobsity.models.Show
import com.rodrigocibils.jobsity.presenters.ShowPresenter

class ShowActivity : AppCompatActivity(), ShowContract.ViewContract {

    private lateinit var binding: ActivityShowBinding

    private lateinit var presenter: ShowContract.PresenterContract

    private val episodes = mutableListOf<Episode>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityShowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = ShowPresenter(this, episodes)

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = resources.getString(R.string.show_details)

        val show = intent.getSerializableExtra("show") as? Show
        show?.let {
            setupView(it)
            setupRecyclerView()
            presenter.getEpisodes(it.id)
        }
    }

    private fun setupRecyclerView() {
        binding.showActivityRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@ShowActivity)
            adapter = EpisodesAdapter(episodes, ::goToEpisodeInfo)
        }
    }

    private fun goToEpisodeInfo(episode: Episode) {
        val intent = Intent(this, EpisodeActivity::class.java)
        intent.putExtra("episode", episode)
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) {
            finish()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun setupView(show: Show) {
        val requestManager = Glide.with(this)
        val requestBuilder = requestManager.load(show.mediumImageUrl).placeholder(R.drawable.ic_launcher_foreground)
        requestBuilder.into(binding.showActivityPoster)

        binding.showActivityName.text = show.name
        binding.showActivityGenres.text =
            String.format(resources.getString(R.string.genre), show.genres.joinToString(", "))
        binding.showActivitySchedule.text =
            String.format(resources.getString(R.string.schedule), show.days.joinToString(", "), show.time)
        binding.showActivitySummary.text = Html.fromHtml(show.summary, Html.FROM_HTML_MODE_COMPACT)
    }

    override fun showLoading() {
        binding.showActivityEpisodesLoading.visibility = View.VISIBLE
    }

    override fun showError() {
        binding.showActivityRecyclerView.visibility = View.GONE
        binding.showActivityEpisodesLoading.visibility = View.GONE
    }

    override fun updateRecyclerView() {
        binding.showActivityEpisodesLoading.visibility = View.GONE
        binding.showActivityRecyclerView.adapter?.notifyDataSetChanged()
    }
}