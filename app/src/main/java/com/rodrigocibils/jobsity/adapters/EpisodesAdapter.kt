package com.rodrigocibils.jobsity.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rodrigocibils.jobsity.R
import com.rodrigocibils.jobsity.models.Episode

class EpisodesAdapter(
    private val episodes: MutableList<Episode>,
    private val callback: (Episode)->Unit
): RecyclerView.Adapter<EpisodesAdapter.ViewHolder>() {

    enum class LayoutType {
        SEPARATOR,
        EPISODE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodesAdapter.ViewHolder {
        val view = if(viewType == LayoutType.EPISODE.ordinal) {
            LayoutInflater.from(parent.context).inflate(R.layout.layout_episode, parent, false)
        } else {
            LayoutInflater.from(parent.context).inflate(R.layout.layout_season_separator, parent, false)
        }

        return ViewHolder(view, parent.context)
    }

    override fun getItemCount(): Int {
        return episodes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(episodes[position], callback)
    }

    override fun getItemViewType(position: Int): Int {
        if(episodes[position].id < 0) {
            return LayoutType.SEPARATOR.ordinal
        }
        return LayoutType.EPISODE.ordinal
    }

    class ViewHolder(
        private val view: View,
        private val context: Context
    ): RecyclerView.ViewHolder(view) {

        fun bind(episode: Episode, callback: (Episode)->Unit) {
            if(episode.id < 0) {
                val separatorText: TextView = view.findViewById(R.id.seasonSeparatorText)
                separatorText.text = String.format(context.getString(R.string.season), episode.season)
            } else {
                val episodeName: TextView = view.findViewById(R.id.episodeName)
                val episodeImage: ImageView = view.findViewById(R.id.episodeImage)

                episodeName.text = String.format(context.getString(R.string.episode), episode.number, episode.name)

                episode.imageUrl?.let {
                    val requestManager = Glide.with(context)
                    val requestBuilder = requestManager.load(it).placeholder(R.drawable.ic_launcher_foreground)
                    requestBuilder.into(episodeImage)
                }

                view.setOnClickListener {
                    callback(episode)
                }
            }
        }
    }
}