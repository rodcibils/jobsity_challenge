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
        if(page == 0) {
            shows.clear()
            view.showLoading()
        }
        searchShowsUseCase.searchShows(page, ::onSearchShowsSuccess, ::onSearchShowsError)
    }

    private fun onSearchShowsSuccess(newShows: List<Show>) {
        cleanupLoadingDummies()
        shows.addAll(newShows)
        view.updateRecyclerView()
    }

    private fun cleanupLoadingDummies() {
        var i = 0
        while(i < shows.size){
            if(shows[i].id < 0) {
                shows.removeAt(i)
            } else {
                ++i
            }
        }
    }

    private fun onSearchShowsError() {
        view.showError()
    }
}