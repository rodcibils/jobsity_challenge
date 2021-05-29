package com.rodrigocibils.jobsity.repositories

import com.rodrigocibils.jobsity.models.Episode
import com.rodrigocibils.jobsity.models.net.ApiEpisode
import com.rodrigocibils.jobsity.net.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EpisodesRepository {

    fun getEpisodes(
        id: Int,
        successCallback: (List<Episode>)->Unit,
        errorCallback: ()->Unit
    ) {
        ApiClient.apiService.getEpisodes(id).enqueue(object: Callback<List<ApiEpisode>> {
            override fun onResponse(
                call: Call<List<ApiEpisode>>,
                response: Response<List<ApiEpisode>>
            ) {
                val episodes = mutableListOf<Episode>()
                val body = response.body()
                if(body != null) {
                    var seasonSeparator = Episode(
                        -1,
                        "",
                        1,
                        1,
                        "",
                        null
                    )
                    episodes.add(seasonSeparator)

                    var lastSeasonQueried = 1
                    body.forEach { curEpisode ->
                        if(curEpisode.season > lastSeasonQueried) {
                            seasonSeparator = Episode(
                                -1,
                                "",
                                curEpisode.season,
                                1,
                                "",
                                null
                            )

                            episodes.add(seasonSeparator)
                            lastSeasonQueried = curEpisode.season
                        }

                        val newEpisode = Episode(
                            curEpisode.id,
                            curEpisode.name,
                            curEpisode.season,
                            curEpisode.number,
                            curEpisode.summary ?: "",
                            curEpisode.image?.medium
                        )

                        episodes.add(newEpisode)
                    }

                    successCallback(episodes.toList())
                } else {
                    errorCallback()
                }
            }

            override fun onFailure(call: Call<List<ApiEpisode>>, t: Throwable) {
                errorCallback()
            }
        })
    }

}