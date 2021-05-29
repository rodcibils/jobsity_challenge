package com.rodrigocibils.jobsity.repositories

import com.rodrigocibils.jobsity.models.Show
import com.rodrigocibils.jobsity.models.net.ApiShow
import com.rodrigocibils.jobsity.net.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShowsRepository {

    fun searchShows(
        page: Int,
        successCallback: (List<Show>)->Unit,
        errorCallback: ()->Unit
    ) {
        ApiClient.apiService.searchShows(page).enqueue(object: Callback<List<ApiShow>> {
            override fun onResponse(call: Call<List<ApiShow>>, response: Response<List<ApiShow>>) {
                val shows = mutableListOf<Show>()
                val body = response.body() as? List<ApiShow>
                if(body != null) {
                    body.forEach { curShow ->
                        val newShow = Show(
                            curShow.id,
                            curShow.name,
                            curShow.image.medium
                        )

                        shows.add(newShow)
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