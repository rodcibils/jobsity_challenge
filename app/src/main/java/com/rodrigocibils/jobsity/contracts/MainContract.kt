package com.rodrigocibils.jobsity.contracts

interface MainContract {
    interface ViewContract {
        fun updateRecyclerView()
        fun showError()
        fun showLoading()
        fun showNoResults()
    }

    interface PresenterContract {
        fun getShows(page: Int)
        fun getShows(query: String)
    }
}