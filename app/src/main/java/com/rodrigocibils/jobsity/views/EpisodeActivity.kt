package com.rodrigocibils.jobsity.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.rodrigocibils.jobsity.R
import com.rodrigocibils.jobsity.databinding.ActivityEpisodeBinding
import com.rodrigocibils.jobsity.models.Episode

class EpisodeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEpisodeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEpisodeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = resources.getString(R.string.episode_details)

        val episode = intent.getSerializableExtra("episode") as? Episode
        episode?.let {
            setupView(it)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) {
            finish()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun setupView(episode: Episode) {
        episode.imageUrl?.let {
            val requestManager = Glide.with(this)
            val requestBuilder = requestManager.load(it).placeholder(R.drawable.ic_launcher_foreground)
            requestBuilder.into(binding.showEpisodeImage)
        }

        binding.showEpisodeName.text = episode.name
        binding.showEpisodeDetails.text = String.format(
            resources.getString(R.string.number_and_season),
            episode.season,
            episode.number
        )
        binding.showEpisodeSummary.text = Html.fromHtml(episode.summary, Html.FROM_HTML_MODE_COMPACT)
    }
}