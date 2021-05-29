package com.rodrigocibils.jobsity.presenters

import com.rodrigocibils.jobsity.contracts.MainContract
import com.rodrigocibils.jobsity.models.Show
import com.rodrigocibils.jobsity.usecases.GetShowsUseCase

class MainPresenter(
    private val view: MainContract.ViewContract,
    private val shows: MutableList<Show>
): MainContract.PresenterContract {

    private val getShowsUseCase = GetShowsUseCase()

    override fun getShows(page: Int) {
        if(page == 0) {
            shows.clear()
            view.showLoading()
        }
        getShowsUseCase.getShows(page, ::onGetShowsSuccess, ::onGetShowsError)
    }

    override fun getShows(query: String) {
        shows.clear()
        view.showLoading()
        getShowsUseCase.getShows(query, ::onGetShowsSearchSuccess, ::onGetShowsError)
    }

    private fun onGetShowsSuccess(newShows: List<Show>) {
        cleanupLoadingDummies()
        shows.addAll(newShows)
        view.updateRecyclerView()
    }

    private fun onGetShowsSearchSuccess(foundShows: List<Show>) {
        if(foundShows.isNotEmpty()) {
            shows.addAll(foundShows)
            view.updateRecyclerView()
        } else {
            view.showNoResults()
        }
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

    private fun onGetShowsError() {
        view.showError()
    }
}