package com.rodrigocibils.jobsity.net

import com.rodrigocibils.jobsity.models.net.ApiEpisode
import com.rodrigocibils.jobsity.models.net.ApiSearch
import com.rodrigocibils.jobsity.models.net.ApiShow
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("shows")
    fun getShows(@Query("page") page: Int): Call<List<ApiShow>>

    @GET("search/shows")
    fun getShows(@Query("q") query: String): Call<List<ApiSearch>>

    @GET("shows/{id}/episodes")
    fun getEpisodes(@Path("id") id: Int): Call<List<ApiEpisode>>
}