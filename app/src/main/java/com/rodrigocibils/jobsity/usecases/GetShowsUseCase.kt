package com.rodrigocibils.jobsity.usecases

import com.rodrigocibils.jobsity.models.Show
import com.rodrigocibils.jobsity.repositories.ShowsRepository

class GetShowsUseCase {

    private val repository = ShowsRepository()

    fun getShows(
        page: Int,
        successCallback: (List<Show>)->Unit,
        errorCallback: ()->Unit
    ) {
        repository.getShows(page, successCallback, errorCallback)
    }

    fun getShows(
        query: String,
        successCallback: (List<Show>) -> Unit,
        errorCallback: () -> Unit
    ) {
        repository.getShows(query, successCallback, errorCallback)
    }
}