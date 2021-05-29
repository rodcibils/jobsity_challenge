package com.rodrigocibils.jobsity.usecases

import com.rodrigocibils.jobsity.models.Show
import com.rodrigocibils.jobsity.repositories.ShowsRepository

class SearchShowsUseCase {

    private val repository = ShowsRepository()

    fun searchShows(
        page: Int,
        successCallback: (List<Show>)->Unit,
        errorCallback: ()->Unit
    ) {
        repository.searchShows(page, successCallback, errorCallback)
    }
}