package com.rodrigocibils.jobsity.contracts

interface MainContract {
    interface ViewContract {
        fun updateRecyclerView()
        fun showError()
        fun showLoading()
    }

    interface PresenterContract {
        fun searchShows(page: Int)
    }
}