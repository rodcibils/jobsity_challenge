package com.rodrigocibils.jobsity.net

import com.rodrigocibils.jobsity.models.net.ApiShow
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("shows")
    fun searchShows(@Query("page") page: Int): Call<List<ApiShow>>
}