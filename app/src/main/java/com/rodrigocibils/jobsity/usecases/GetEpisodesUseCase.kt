package com.rodrigocibils.jobsity.usecases

import com.rodrigocibils.jobsity.models.Episode
import com.rodrigocibils.jobsity.repositories.EpisodesRepository

class GetEpisodesUseCase {

    private val repository = EpisodesRepository()

    fun getEpisodes(
        id: Int,
        successCallback: (List<Episode>)->Unit,
        errorCallback: ()->Unit
    ) {
        repository.getEpisodes(id, successCallback, errorCallback)
    }
}