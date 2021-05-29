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
import com.rodrigocibils.jobsity.models.Show

class ShowsAdapter(
    private val shows: MutableList<Show>
):  RecyclerView.Adapter<ShowsAdapter.ViewHolder>() {

    enum class LayoutType {
        SHOW,
        LOADING
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = if(viewType == LayoutType.SHOW.ordinal) {
            LayoutInflater.from(parent.context).inflate(R.layout.layout_show, parent, false)
        } else {
            LayoutInflater.from(parent.context).inflate(R.layout.layout_loading, parent, false)
        }

        return ViewHolder(view, parent.context)
    }

    override fun getItemCount(): Int {
        return shows.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(shows[position].id >= 0) {
            holder.bind(shows[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        if(shows[position].id < 0) {
            return LayoutType.LOADING.ordinal
        }
        return LayoutType.SHOW.ordinal
    }

    class ViewHolder(
        private val view: View,
        private val context: Context
    ): RecyclerView.ViewHolder(view) {

        fun bind(show: Show){
            val image: ImageView = view.findViewById(R.id.layoutShowImage)
            val name: TextView = view.findViewById(R.id.layoutShowName)
            name.text = show.name

            val requestManager = Glide.with(context)
            val requestBuilder = requestManager.load(show.imageUrl).placeholder(R.drawable.ic_launcher_foreground)
            requestBuilder.into(image)
        }
    }
}