package com.rodrigocibils.jobsity.presenters

import com.rodrigocibils.jobsity.contracts.ShowContract
import com.rodrigocibils.jobsity.models.Episode
import com.rodrigocibils.jobsity.usecases.GetEpisodesUseCase

class ShowPresenter(
    private val view: ShowContract.ViewContract,
    private val episodes: MutableList<Episode>
): ShowContract.PresenterContract {

    private val getEpisodesUseCase = GetEpisodesUseCase()

    override fun getEpisodes(id: Int) {
        view.showLoading()
        episodes.clear()
        getEpisodesUseCase.getEpisodes(id, ::onGetEpisodesSuccess, ::onGetEpisodesError)
    }

    private fun onGetEpisodesSuccess(newEpisodes: List<Episode>) {
        episodes.addAll(newEpisodes)
        view.updateRecyclerView()
    }

    private fun onGetEpisodesError() {
        view.showError()
    }
}