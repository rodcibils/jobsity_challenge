package com.rodrigocibils.jobsity.presenters

import com.rodrigocibils.jobsity.contracts.MainContract
import com.rodrigocibils.jobsity.models.Show
import com.rodrigocibils.jobsity.usecases.SearchShowsUseCase

class MainPresenter(
    private val view: MainContract.ViewContract,
    private val shows: MutableList<Show>
): MainContract.PresenterContract {

    private val searchShowsUseCase = SearchShowsUseCase()

    override fun searchShows(page: Int) {
        searchShowsUseCase.searchShows(page, ::onSearchShowsSuccess, ::onSearchShowsError)
    }

    private fun onSearchShowsSuccess(newShows: List<Show>) {
        shows.addAll(newShows)
        view.updateRecyclerView()
    }

    private fun onSearchShowsError() {
        view.showError()
    }
}