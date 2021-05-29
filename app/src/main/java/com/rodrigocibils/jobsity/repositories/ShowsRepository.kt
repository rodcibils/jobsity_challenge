package com.rodrigocibils.jobsity.repositories

import com.rodrigocibils.jobsity.models.Show
import com.rodrigocibils.jobsity.models.net.ApiShow
import com.rodrigocibils.jobsity.net.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShowsRepository {

    fun getShows(
        page: Int,
        successCallback: (List<Show>)->Unit,
        errorCallback: ()->Unit
    ) {
        ApiClient.apiService.getShows(page).enqueue(object: Callback<List<ApiShow>> {
            override fun onResponse(call: Call<List<ApiShow>>, response: Response<List<ApiShow>>) {
                val shows = mutableListOf<Show>()
                val body = response.body() as? List<ApiShow>
                if(body != null) {
                    body.forEach { curShow ->
                        val newShow = Show(
                            curShow.id,
                            curShow.name,
                            curShow.image?.medium,
                            curShow.summary,
                            curShow.schedule.time,
                            curShow.schedule.days,
                            curShow.genres
                        )

                        shows.add(newShow)
                    }

                    if(body.isNotEmpty()) {
                        val loadingDummy = Show(
                            -1,
                            "",
                            null,
                            "",
                            "",
                            emptyList(),
                            emptyList()
                        )
                        shows.add(loadingDummy)
                    }

                    successCallback(shows.toList())
                } else {
                    errorCallback()
                }
            }

            override fun onFailure(call: Call<List<ApiShow>>, t: Throwable) {
                errorCallback()
            }
        })
    }

}