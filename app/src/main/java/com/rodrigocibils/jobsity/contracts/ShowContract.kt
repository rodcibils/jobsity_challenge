package com.rodrigocibils.jobsity.contracts

interface ShowContract {
    interface ViewContract {
        fun showLoading()
        fun showError()
        fun updateRecyclerView()
    }

    interface PresenterContract {
        fun getEpisodes(id: Int)
    }
}